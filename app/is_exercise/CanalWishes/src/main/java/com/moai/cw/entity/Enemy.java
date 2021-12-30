package com.moai.cw.entity;

import com.moai.cw.Constants;
import com.moai.cw.game_object.Spawner;
import com.moai.cw.interfaces.OffScreenListener;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Direction.DIRECTION;

public abstract class Enemy extends Rigitbody implements OffScreenListener {
  protected DIRECTION direction;
  private int type;
  private Spawner spawner;

  public Enemy(Scene scene, Spawner spawner, DVector2 position, CVector2 textureCoordinate,
      CVector2 textureSize, int textureIndex, DIRECTION direction, int type) {
    super(scene, null, position, new DVector2(1, 1), textureCoordinate, textureSize, textureIndex, 1, 1);
    this.direction = direction;
    this.type = type;
    this.spawner = spawner;
  }

  @Override
  public int getLayer() {
    return Constants.LAYER_ENEMY;
  }

  protected int getType() {
    return type;
  }

  @Override
  public void onOffScreen() {
    destroy();
    spawner.onChildDestroyed();
  }
}
