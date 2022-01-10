package com.moai.cw.fireball;

import com.moai.cw.Constants;
import com.moai.cw.entity.Enemy;
import com.moai.cw.entity.Entity;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.interfaces.Hittable;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Direction.DIRECTION;

public abstract class Fireball extends Entity implements Hittable {
  private int direction;
  private int power;

  public Fireball(Scene scene, DVector2 position, CVector2 textureCoordinate,
      CVector2 textureSize, int textureIndex, DIRECTION direction, int power) {
    super(scene, null, position, new DVector2(1, 1), textureCoordinate, textureSize, textureIndex);
    this.direction = direction == DIRECTION.LEFT ? -1 : 1;
    this.power = power;
  }

  @Override
  public int getLayer() {
    return Constants.LAYER_FIREBALL;
  }

  @Override
  public void onHit(GameObject target) {
    if (target instanceof Enemy) {
      ((Enemy) target).damage(power, direction);
      destroy();
    }
  }

  public int getDirection() {
    return direction;
  }
}
