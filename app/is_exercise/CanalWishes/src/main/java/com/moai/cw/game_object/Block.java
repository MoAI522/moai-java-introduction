package com.moai.cw.game_object;

import com.moai.cw.Constants;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CPosition;
import com.moai.cw.util.DPosition;

public class Block extends Entity {
  public Block(Scene scene, GameObject parent, DPosition position, int blockIndex) {
    super(scene, parent, position, new DPosition(1, 1),
        new CPosition(blockIndex % Constants.MAPCHIP_COLUMN * Constants.MAPCHIP_SIZE,
            blockIndex / Constants.MAPCHIP_COLUMN * Constants.MAPCHIP_SIZE),
        new CPosition(Constants.MAPCHIP_SIZE, Constants.MAPCHIP_SIZE), 0);
  }

  @Override
  public void update(int dt) {

  }
}
