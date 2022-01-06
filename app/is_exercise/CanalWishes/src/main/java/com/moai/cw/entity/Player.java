package com.moai.cw.entity;

import java.util.HashMap;

import com.moai.cw.Constants;
import com.moai.cw.KeyInputManager.KeyCode;
import com.moai.cw.fireball.Bress;
import com.moai.cw.fireball.Vomit;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.game_object.Hitbox;
import com.moai.cw.interfaces.Hittable;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.Color;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Direction.DIRECTION;

public class Player extends Rigitbody implements Hittable {
  private static double JUMP_FORCE = 0.3;
  private static double HOVER_FORCE = 0.3;
  private static double DEFAULT_DRAG = 1.05;
  private static double HOVER_DRAG = 1.3;
  private static double MASS = 1;
  private static double WALK_SPEED = 3;
  private static double SPRINT_SPEED = 8;
  private static double EATING_WALK_SPEED = 1.5;
  private static double SIZE = 24;
  private static double VACCUME_OFFSET_X = 16;
  private static double VACCUME_OFFSET_Y = -28;
  private static double VACCUME_SIZE_X = 80;
  private static double VACCUME_SIZE_Y = 80;
  private static int HOVER_COOLTIME = 5;
  private static int WALK_TO_SPRINT = 3;
  private static int HORIZONTAL_VELOCITY_CHANGE_DURATION = 3;

  private enum State {
    STOP, WALK, SPRINT, JUMP, FALL, HOVER, VACCUMING
  }

  private State state;
  private FrameCounter counter;
  private int direction;
  private double previousHorizontalVelocity, targetHorizontalVelocity;
  private VacuumBox vacuumBox;
  private boolean isEating;

  public Player(Scene scene, DVector2 position) {
    super(scene, null, position, new DVector2(1, 1), new CVector2(0, 0), new CVector2(24, 24), 1, MASS, DEFAULT_DRAG);
    state = State.STOP;
    counter = new FrameCounter();
    direction = 1;
    previousHorizontalVelocity = 0;
    targetHorizontalVelocity = 0;
    vacuumBox = null;
    isEating = false;
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
        if (!isEating && counter.get(FrameCounter.KEY.LAST_HOVER) > HOVER_COOLTIME) {
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
      if (isEating) {
        disgorge();
      } else {
        if (isAirborne()) {
          if (state == State.HOVER) {
            stopHovering();
            state = State.JUMP;
          }
        } else {
          if (state != State.VACCUMING) {
            state = State.VACCUMING;
            vacuumBox = new VacuumBox();
            changeSpeed(0, false);
          }
        }
      }
    } else if (getScene().getApp().getKeyInputManager().getState(KeyCode.B) == -1) {
      if (state == State.VACCUMING) {
        state = State.STOP;
        if (vacuumBox != null) {
          vacuumBox.destroy();
          vacuumBox = null;
        }
      }
    }

    physics(dt);
    getScene().getApp().setDebugInfo("player-pos", getPosition().toString());
    getScene().getApp().setDebugInfo("player-state", state.toString());
    getScene().getApp().setDebugInfo("player-eating", Boolean.toString(isEating));
  }

  private void handleHorizontalMove() {
    if (state == State.VACCUMING)
      return;
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
        if (inputCount == 1 && this.direction == direction && !isEating) {
          changeSpeed(direction * SPRINT_SPEED, false);
          state = State.SPRINT;
        } else {
          if (isEating) {
            changeSpeed(direction * EATING_WALK_SPEED, false);
          } else {
            changeSpeed(direction * WALK_SPEED, false);
          }
          counter.reset(FrameCounter.KEY.LAST_WALK);
        }
        break;
      case SPRINT:
        changeSpeed(direction * SPRINT_SPEED, false);
        break;
      case JUMP:
      case HOVER:
      case FALL:
        if (isEating) {
          changeSpeed(direction * EATING_WALK_SPEED, false);
        } else {
          changeSpeed(direction * WALK_SPEED, false);
        }
        if (!isAirborne()) {
          state = State.WALK;
        }
        break;
      default:
    }
    this.direction = direction;
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
    new Bress(getScene(),
        getPosition().add(new DVector2(direction == -1 ? -Bress.SIZE : getSize().x, getSize().y / 2 - Bress.SIZE / 2)),
        direction == -1 ? DIRECTION.LEFT : DIRECTION.RIGHT);
  }

  private void disgorge() {
    new Vomit(getScene(),
        getPosition().add(new DVector2(direction == -1 ? -Vomit.SIZE : getSize().x, getSize().y / 2 - Vomit.SIZE / 2)),
        direction == -1 ? DIRECTION.LEFT : DIRECTION.RIGHT);
    isEating = false;
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

  public class VacuumBox extends Hitbox {
    public VacuumBox() {
      super(Player.this.getScene(), Player.this,
          new DVector2(direction == -1 ? SIZE - VACCUME_OFFSET_X - VACCUME_SIZE_X : VACCUME_OFFSET_X, VACCUME_OFFSET_Y),
          new DVector2(VACCUME_SIZE_X, VACCUME_SIZE_Y), new Color(1, 0, 0));
    }

    @Override
    public void update(int dt) {
    }

    @Override
    public void onHit(GameObject target) {
      if (!(target instanceof Enemy))
        return;
      ((Enemy) target).onVaccumed(Player.this.getPosition());
      destroy();
    }

  }

  @Override
  public int getLayer() {
    return Constants.LAYER_PLAYER;
  }

  @Override
  public void onHit(GameObject target) {
    if (target instanceof Enemy) {
      Enemy enemy = (Enemy) target;
      if (enemy.isVaccumed()) {
        state = State.STOP;
        isEating = true;
        enemy.kill();
      }
    }
  }
}
