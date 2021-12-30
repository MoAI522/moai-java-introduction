package com.moai.cw.entity;

import java.util.HashMap;

import com.moai.cw.Constants;
import com.moai.cw.KeyInputManager.KeyCode;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;

public class Player extends Rigitbody {
  private static double JUMP_FORCE = 0.3;
  private static double HOVER_FORCE = 0.3;
  private static double DEFAULT_DRAG = 1.05;
  private static double HOVER_DRAG = 1.3;
  private static double MASS = 1;
  private static double WALK_SPEED = 3;
  private static double SPRINT_SPEED = 8;
  private static int HOVER_COOLTIME = 5;
  private static int WALK_TO_SPRINT = 5;
  private static int HORIZONTAL_VELOCITY_CHANGE_DURATION = 5;

  private enum State {
    STOP, WALK, SPRINT, JUMP, FALL, HOVER
  }

  private State state;
  private FrameCounter counter;
  private boolean leftward;
  private double previousHorizontalVelocity, targetHorizontalVelocity;

  public Player(Scene scene, DVector2 position) {
    super(scene, null, position, new DVector2(1, 1), new CVector2(0, 0), new CVector2(24, 24), 1, MASS, DEFAULT_DRAG);
    state = State.STOP;
    counter = new FrameCounter();
    leftward = false;
    previousHorizontalVelocity = 0;
    targetHorizontalVelocity = 0;
  }

  @Override
  public void update(int dt) {
    counter.update();

    DVector2 velocity = getVelocity();

    if (isAirborne()) {
      switch (state) {
        case WALK:
        case SPRINT:
          state = State.JUMP;
          break;
        case JUMP:
          if (velocity.y > 0) {
            state = State.FALL;
          }
          break;
        default:
      }
    } else {
      switch (state) {
        case HOVER:
          stopHovering();
          state = State.STOP;
          break;
        default:
      }
    }

    handleHorizontalMove();
    velocity.x = previousHorizontalVelocity + (targetHorizontalVelocity - previousHorizontalVelocity)
        * (Math.min(counter.get(FrameCounter.KEY.LAST_HORIZONTAL_VELOCITY_CHANGE), HORIZONTAL_VELOCITY_CHANGE_DURATION)
            / HORIZONTAL_VELOCITY_CHANGE_DURATION);
    setVelocity(velocity);

    if (getScene().getApp().getKeyInputManager().getState(KeyCode.SPACE) == 1) {
      if (isAirborne()) {
        if (counter.get(FrameCounter.KEY.LAST_HOVER) > HOVER_COOLTIME) {
          addForce(new DVector2(0, -HOVER_FORCE));
          setDrag(HOVER_DRAG);
          state = State.HOVER;
          counter.reset(FrameCounter.KEY.LAST_HOVER);
        }
      } else {
        addForce(new DVector2(0, -JUMP_FORCE));
        state = State.JUMP;
        counter.reset(FrameCounter.KEY.LAST_HOVER);
      }
    }

    if (getScene().getApp().getKeyInputManager().getState(KeyCode.B) == 1) {
      if (isAirborne()) {
        if (state == State.HOVER) {
          stopHovering();
          state = State.JUMP;
        }
      }
    }

    physics(dt);
    getScene().getApp().setDebugInfo("player-pos", getPosition().toString());
    getScene().getApp().setDebugInfo("player-state", state.toString());
  }

  private void handleHorizontalMove() {
    int direction = 0;
    int inputCount = 0;
    if (getScene().getApp().getKeyInputManager().getState(KeyCode.A) > 0) {
      direction -= 1;
      inputCount = getScene().getApp().getKeyInputManager().getState(KeyCode.A);
    }
    if (getScene().getApp().getKeyInputManager().getState(KeyCode.D) > 0) {
      direction += 1;
      inputCount = getScene().getApp().getKeyInputManager().getState(KeyCode.D);
    }
    if (direction == 0) {
      if (counter.get(FrameCounter.KEY.LAST_WALK) > WALK_TO_SPRINT) {
        if (!isAirborne()) {
          state = State.STOP;
        }
        changeSpeed(0, false);
      }
      return;
    }

    switch (state) {
      case STOP:
        changeSpeed(direction * WALK_SPEED, true);
        if (!isAirborne()) {
          state = State.WALK;
          counter.reset(FrameCounter.KEY.LAST_WALK);
        }
        break;
      case WALK:
        if (inputCount == 1 && leftward == (direction == -1)) {
          changeSpeed(direction * SPRINT_SPEED, false);
          state = State.SPRINT;
        } else {
          changeSpeed(direction * WALK_SPEED, false);
          counter.reset(FrameCounter.KEY.LAST_WALK);
        }
        break;
      case SPRINT:
        changeSpeed(direction * SPRINT_SPEED, false);
        break;
      case JUMP:
      case HOVER:
      case FALL:
        changeSpeed(direction * WALK_SPEED, true);
        break;
    }
    leftward = (direction == -1);
  }

  private void changeSpeed(double v, boolean eventually) {
    if (targetHorizontalVelocity == v)
      return;
    if (eventually) {
      previousHorizontalVelocity = v;
      targetHorizontalVelocity = v;
      return;
    }
    previousHorizontalVelocity = targetHorizontalVelocity;
    targetHorizontalVelocity = v;
    counter.reset(FrameCounter.KEY.LAST_HORIZONTAL_VELOCITY_CHANGE);
  }

  private void stopHovering() {
    setDrag(DEFAULT_DRAG);
  }

  private static class FrameCounter {
    private HashMap<KEY, Integer> counts;

    public static enum KEY {
      LAST_HOVER,
      LAST_WALK,
      LAST_HORIZONTAL_VELOCITY_CHANGE
    }

    public FrameCounter() {
      counts = new HashMap<KEY, Integer>();
      for (KEY key : KEY.values()) {
        counts.put(key, 0);
      }
    }

    public void update() {
      for (KEY key : KEY.values()) {
        counts.put(key, counts.get(key) + 1);
      }
    }

    public void reset(KEY key) {
      counts.put(key, 0);
    }

    public int get(KEY key) {
      return counts.get(key);
    }
  }

  @Override
  public int getLayer() {
    return Constants.LAYER_PLAYER;
  }
}
