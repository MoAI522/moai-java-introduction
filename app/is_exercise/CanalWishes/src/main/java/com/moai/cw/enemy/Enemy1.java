package com.moai.cw.enemy;

import com.moai.cw.entity.Enemy;
import com.moai.cw.game_object.Spawner;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Direction.DIRECTION;

public class Enemy1 extends Enemy {
  public static final double WALK_SPEED = 1;

  public Enemy1(Scene scene, Spawner spawner, DVector2 position, DIRECTION direction, int type) {
    super(scene, spawner, position, new CVector2(120, 0), new CVector2(24, 24), 0, direction, type);
  }

  @Override
  public void update(int dt) {
    switch (getType()) {
      case 0: {
        setVelocity(new DVector2((direction == DIRECTION.LEFT ? -1 : 1) * WALK_SPEED, getVelocity().y));
        break;
      }
    }

    physics(dt);
  }
}