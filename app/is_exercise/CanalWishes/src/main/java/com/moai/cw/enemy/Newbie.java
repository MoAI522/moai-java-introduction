package com.moai.cw.enemy;

import com.moai.cw.entity.Enemy;
import com.moai.cw.game_object.Spawner;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Direction.DIRECTION;

public class Newbie extends Enemy {
  private static final double WALK_SPEED = 0.5;
  private static final int SIZE = 24;
  private static final int ANIMATION_FREQUENCY = 8;
  private static final int ANIMATION_WALK_NUMBER = 2;

  private int animation_count = 0;

  public Newbie(Scene scene, Spawner spawner, DVector2 position, DIRECTION direction, int type) {
    super(scene, spawner, position, new CVector2(0, 72), new CVector2(SIZE, SIZE), 1, direction, type, 30);
  }

  @Override
  public void enemyUpdate(boolean vaccumed, boolean isKnockbacked) {
    if (vaccumed || isKnockbacked) {
      setTextureCoordinate(new CVector2(SIZE, 72));
      setIsReverse(direction == -1);
      return;
    }

    switch (getType()) {
      case 0: {
        setVelocity(new DVector2(direction * WALK_SPEED, getVelocity().y));
        break;
      }
    }

    setTextureCoordinate(new CVector2(0, 72 + SIZE * (int) Math
        .floor((double) (animation_count % ANIMATION_FREQUENCY) / (ANIMATION_FREQUENCY / ANIMATION_WALK_NUMBER))));
    setIsReverse(direction == -1);
    animation_count++;
  }
}
