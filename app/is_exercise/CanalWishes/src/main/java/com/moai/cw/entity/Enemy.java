package com.moai.cw.entity;

import com.moai.cw.Constants;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.game_object.Spawner;
import com.moai.cw.interfaces.Hittable;
import com.moai.cw.interfaces.OffScreenListener;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Direction.DIRECTION;

public abstract class Enemy extends Rigitbody implements OffScreenListener, Hittable {
  private static final int KNOCKBACK_DURATION = 15;
  private static final double KNOCKBACK_INITIAL_VELOCITY = 8;

  protected int direction;
  private int type;
  private Spawner spawner;
  private boolean vaccumable;
  private boolean vaccumed;
  private int hp;
  private int knockbackCount;

  public Enemy(Scene scene, Spawner spawner, DVector2 position, CVector2 textureCoordinate,
      CVector2 textureSize, int textureIndex, DIRECTION direction, int type, int hp) {
    super(scene, null, position, new DVector2(1, 1), textureCoordinate, textureSize, textureIndex, 1, 1);
    this.direction = direction == DIRECTION.LEFT ? -1 : 1;
    this.type = type;
    this.spawner = spawner;
    this.vaccumable = true;
    this.vaccumed = false;
    this.hp = hp;
    this.knockbackCount = -1;
  }

  @Override
  public final void update(int dt) {
    if (knockbackCount != -1) {
      knockbackCount++;
      DVector2 v = getVelocity();
      v.x = -direction * KNOCKBACK_INITIAL_VELOCITY * (1 - ((double) knockbackCount / KNOCKBACK_DURATION));
      setVelocity(v);
      if (knockbackCount == KNOCKBACK_DURATION) {
        knockbackCount = -1;
      }
    }
    enemyUpdate(isVaccumed(), knockbackCount != -1);
    physics(dt);
  }

  protected abstract void enemyUpdate(boolean vaccumed, boolean isKnockBacked);

  @Override
  public int getLayer() {
    return Constants.LAYER_ENEMY;
  }

  protected int getType() {
    return type;
  }

  public boolean isVaccumed() {
    return vaccumed;
  }

  protected void setVaccumable(boolean vaccumable) {
    this.vaccumable = vaccumable;
  }

  public void kill() {
    destroy();
    spawner.onChildDestroyed();
  }

  @Override
  public void onOffScreen() {
    kill();
  }

  public void onVaccumed(DVector2 to) {
    if (!vaccumable)
      return;
    this.vaccumed = true;
    final DVector2 currentPosition = getPosition();
    Thread th = new Thread() {
      public void run() {
        final int duration = 30;
        for (int c = 0; c < duration; c++) {
          setPosition(currentPosition
              .add(to.add(currentPosition.negate())
                  .multipleScalar(1 - Math.cos(Math.PI / 2 * ((double) c / duration)))));
          try {
            sleep(1000 / Constants.TARGET_FPS);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    };
    th.start();
  }

  @Override
  public void onHit(GameObject target) {

  }

  public void damage(int value, int direction) {
    hp -= value;
    this.direction = -direction;
    knockbackCount = 0;
    if (hp <= 0) {
      kill();
    }
  }
}
