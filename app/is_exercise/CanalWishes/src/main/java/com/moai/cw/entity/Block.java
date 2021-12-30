package com.moai.cw.entity;

import com.moai.cw.Constants;
import com.moai.cw.game_object.Entity;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;

public class Block extends Entity {
  public Block(Scene scene, GameObject parent, DVector2 position, int blockIndex) {
    super(scene, parent, position, new DVector2(1, 1),
        new CVector2(blockIndex % Constants.MAPCHIP_COLUMN * Constants.MAPCHIP_SIZE,
            blockIndex / Constants.MAPCHIP_COLUMN * Constants.MAPCHIP_SIZE),
        new CVector2(Constants.MAPCHIP_SIZE, Constants.MAPCHIP_SIZE), 0);
  }

  @Override
  public void update(int dt) {

  }

  @Override
  public int getLayer() {
    return 0;
  }
}
