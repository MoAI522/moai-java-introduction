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

  private int tempDirection;

  public Fish(Scene scene, Spawner spawner, DVector2 position, DIRECTION direction, int type) {
    super(scene, spawner, position, new CVector2(120, 24), new CVector2(24, 24), 0, direction, type);
    tempDirection = direction == DIRECTION.LEFT ? -1 : 1;
  }

  @Override
  public void update(int dt) {
    if (isVaccumed())
      return;
    switch (getType()) {
      case 0: {
        if (!isAirborne()) {
          addForce(new DVector2(0, -JUMP_FORCE));
          tempDirection = Math.random() < 0.5 ? -1 : 1;
        }
        setVelocity(new DVector2(tempDirection * HORIZONTAL_MOVE_SPEED, getVelocity().y));
        break;
      }
    }

    physics(dt);
  }
}
