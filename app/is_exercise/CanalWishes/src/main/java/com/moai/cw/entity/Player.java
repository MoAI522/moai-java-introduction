package com.moai.cw.entity;

import java.util.HashMap;

import com.moai.cw.Constants;
import com.moai.cw.KeyInputManager.KeyCode;
import com.moai.cw.field_object.Door;
import com.moai.cw.fireball.Bress;
import com.moai.cw.fireball.Vomit;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.game_object.Hitbox;
import com.moai.cw.interfaces.EventArea;
import com.moai.cw.interfaces.Hittable;
import com.moai.cw.scene.FieldScene;
import com.moai.cw.store.FieldStore;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.Color;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Direction.DIRECTION;

public class Player extends Rigitbody implements Hittable {
  private static final double JUMP_FORCE = 0.3;
  private static final double HOVER_FORCE = 0.3;
  private static final double DEFAULT_DRAG = 1.05;
  private static final double HOVER_DRAG = 1.3;
  private static final double MASS = 1;
  private static final double WALK_SPEED = 3;
  private static final double SPRINT_SPEED = 8;
  private static final double EATING_WALK_SPEED = 1.5;
  private static final double SIZE = 24;
  private static final double VACCUME_OFFSET_X = 16;
  private static final double VACCUME_OFFSET_Y = -28;
  private static final double VACCUME_SIZE_X = 80;
  private static final double VACCUME_SIZE_Y = 80;
  private static final int HOVER_COOLTIME = 5;
  private static final int WALK_TO_SPRINT = 3;
  private static final int HORIZONTAL_VELOCITY_CHANGE_DURATION = 3;
  private static final int DAMAGE_COOLTIME = 50;
  private static final int DAMAGE_CONTROL_COOLTIME = 10;
  private static final double DAMAGE_KNOCKBACK_INITIAL_VELOCITY = 5;

  private enum State {
    STOP, WALK, SPRINT, JUMP, FALL, HOVER, VACCUMING, DAMAGED
  }

  private State state;
  private FrameCounter counter;
  private int direction;
  private double previousHorizontalVelocity, targetHorizontalVelocity;
  private VacuumBox vacuumBox;
  private boolean isEating;

  private FieldStore store;

  public Player(FieldScene scene, DVector2 position) {
    super(scene, null, position, new DVector2(1, 1), new CVector2(0, 0), new CVector2(24, 24), 1, MASS, DEFAULT_DRAG);
    state = State.STOP;
    counter = new FrameCounter();
    counter.set(FrameCounter.KEY.LAST_DAMAGED, DAMAGE_COOLTIME);
    direction = 1;
    previousHorizontalVelocity = 0;
    targetHorizontalVelocity = 0;
    vacuumBox = null;
    isEating = false;
    store = scene.getStore();
  }

  @Override
  public void update(int dt) {
    counter.update();

    DVector2 velocity = getVelocity();

    if (state == State.DAMAGED) {
      if (counter.get(FrameCounter.KEY.LAST_DAMAGED) > DAMAGE_CONTROL_COOLTIME) {
        state = State.STOP;
      }
    }
    boolean isLocked = counter.get(FrameCounter.KEY.LAST_DAMAGED) <= DAMAGE_CONTROL_COOLTIME;

    if (counter.get(FrameCounter.KEY.LAST_DAMAGED) < DAMAGE_COOLTIME) {
      setVisibility(counter.get(FrameCounter.KEY.LAST_DAMAGED) % 4 <= 1);
    } else {
      setVisibility(true);
    }

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
        * (Math.min(counter.get(FrameCounter.KEY.LAST_HORIZONTAL_VELOCITY_CHANGE),
            HORIZONTAL_VELOCITY_CHANGE_DURATION)
            / HORIZONTAL_VELOCITY_CHANGE_DURATION);
    setVelocity(velocity);

    if (!isLocked) {
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

      if (getScene().getApp().getKeyInputManager().getState(KeyCode.W) == 1) {
        enterDoor();
      }

      if (getScene().getApp().getKeyInputManager().getState(KeyCode.S) >= 1) {
        if (isOnHalfFloor()) {
          setPosition(getPosition().add(new DVector2(0, 1)));
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
    if (state == State.DAMAGED) {
      changeSpeed(-direction * DAMAGE_KNOCKBACK_INITIAL_VELOCITY
          * (1 - ((double) counter.get(FrameCounter.KEY.LAST_DAMAGED)
              / DAMAGE_CONTROL_COOLTIME)),
          true);
      return;
    }
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

  private void enterDoor() {
    if (!(getScene() instanceof FieldScene))
      return;
    FieldScene scene = (FieldScene) getScene();
    EventArea area = scene.getEventAreaManager().checkArea(getRectangle());
    if (area == null)
      return;
    if (!(area instanceof Door))
      return;
    Door door = (Door) area;
    scene.moveStage(door.getLink(), door.getDestination());
  }

  private static class FrameCounter {
    private HashMap<KEY, Integer> counts;

    public static enum KEY {
      LAST_HOVER,
      LAST_WALK,
      LAST_HORIZONTAL_VELOCITY_CHANGE,
      LAST_DAMAGED
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

    public void set(KEY key, int val) {
      counts.put(key, val);
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
      } else {
        if (counter.get(FrameCounter.KEY.LAST_DAMAGED) > DAMAGE_COOLTIME) {
          direction = getPosition().x > enemy.getPosition().x ? -1 : 1;
          store.changePlayerHP(-10);
          enemy.damage(10, direction);
          if (vacuumBox != null) {
            vacuumBox.destroy();
            vacuumBox = null;
          }
          state = State.DAMAGED;
          counter.reset(FrameCounter.KEY.LAST_DAMAGED);
        }
      }
    }
  }
}
