package com.moai.cw.game_object;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.moai.cw.scene.Scene;
import com.moai.cw.util.DVector2;

public class BlockInformationManager extends GameObject {
  private int[] types;

  public BlockInformationManager(Scene scene) {
    super(scene, new DVector2(0, 0));
    try {
      InputStreamReader isr = new InputStreamReader(
          Thread.currentThread().getContextClassLoader().getResourceAsStream("block_types.csv"));
      BufferedReader br = new BufferedReader(isr);
      String line = br.readLine();
      String[] split = line.split(",");
      types = new int[split.length];
      for (int i = 0; i < split.length; i++) {
        types[i] = Integer.valueOf(split[i]);
      }
      br.close();
      isr.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(int dt) {

  }

  public int getType(int index) {
    if (index < types.length) {
      return types[index];
    } else {
      return 0;
    }
  }
}
