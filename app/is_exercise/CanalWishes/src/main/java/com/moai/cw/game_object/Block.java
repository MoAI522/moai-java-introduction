package com.moai.cw.game_object;

import com.moai.cw.scene.Scene;
import com.moai.cw.util.CPosition;
import com.moai.cw.util.DPosition;

public class Block extends Entity {
  public Block(Scene scene, GameObject parent, DPosition position, int blockIndex) {
    super(scene, parent, position, new DPosition(1, 1),
        new CPosition(blockIndex % 8 * 32, blockIndex / 8 * 32), new CPosition(32, 32), 0);
  }

  @Override
  public void update(double dt) {

  }
}
