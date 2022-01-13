package com.moai.cw.enemy;

import com.moai.cw.entity.Enemy;
import com.moai.cw.game_object.Spawner;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Direction.DIRECTION;

public class Fish extends Enemy {
  private static final double HORIZONTAL_MOVE_SPEED = 0.3;
  private static final double JUMP_FORCE = 0.15;
  private static final int SIZE_W = 24;
  private static final int SIZE_H = 12;
  private static final int ANIMATION_FALL_DELAY = 10;
  private static final int ANIMATION_STRUGGLE_FREQUENCY = 4;
  private static final int ANIMATION_STRUGGLE_NUMBER = 2;

  private int struggle_count = 0;
  private int airborne_count = ANIMATION_FALL_DELAY;

  public Fish(Scene scene, Spawner spawner, DVector2 position, DIRECTION direction, int type) {
    super(scene, spawner, position, new CVector2(48, 72), new CVector2(SIZE_W, SIZE_H), 1, direction, type, 30);
  }

  @Override
  public void enemyUpdate(boolean vaccumed, boolean isKnockbacked) {
    if (vaccumed || isKnockbacked) {
      setTextureCoordinate(
          new CVector2(48, 72 + SIZE_H * (int) Math.floor((double) (struggle_count % ANIMATION_STRUGGLE_FREQUENCY)
              / (ANIMATION_STRUGGLE_FREQUENCY / ANIMATION_STRUGGLE_NUMBER))));
      setIsReverse(direction == -1);
      struggle_count++;
      return;
    }

    switch (getType()) {
      case 0: {
        if (!isAirborne()) {
          addForce(new DVector2(0, -JUMP_FORCE));
          direction = Math.random() < 0.5 ? -1 : 1;
          airborne_count = 0;
        }
        setVelocity(new DVector2(direction * HORIZONTAL_MOVE_SPEED, getVelocity().y));
        break;
      }
    }

    setTextureCoordinate(new CVector2(48, 72 + SIZE_H * (airborne_count < ANIMATION_FALL_DELAY ? 0 : 1)));
    setIsReverse(direction == -1);
    airborne_count++;
  }
}
