package com.moai.cw.fireball;

import com.moai.cw.Constants;
import com.moai.cw.entity.Entity;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;

public abstract class Fireball extends Entity {

  public Fireball(Scene scene, DVector2 position, CVector2 textureCoordinate,
      CVector2 textureSize, int textureIndex) {
    super(scene, null, position, new DVector2(1, 1), textureCoordinate, textureSize, textureIndex);

  }

  @Override
  public int getLayer() {
    return Constants.LAYER_FIREBALL;
  }
}
