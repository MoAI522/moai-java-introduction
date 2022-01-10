package com.moai.cw.enemy;

import com.moai.cw.entity.Enemy;
import com.moai.cw.game_object.Spawner;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Direction.DIRECTION;

public class Newbie extends Enemy {
  public static final double WALK_SPEED = 0.5;

  public Newbie(Scene scene, Spawner spawner, DVector2 position, DIRECTION direction, int type) {
    super(scene, spawner, position, new CVector2(120, 0), new CVector2(24, 24), 0, direction, type, 30);
  }

  @Override
  public void enemyUpdate() {
    switch (getType()) {
      case 0: {
        setVelocity(new DVector2(direction * WALK_SPEED, getVelocity().y));
        break;
      }
    }
  }
}
