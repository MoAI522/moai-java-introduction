package com.moai.cw.game_object;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

import com.moai.cw.Constants;
import com.moai.cw.entity.Block;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.DVector2;

public class Stage extends GameObject {
  private char[][] mapData;

  public Stage(Scene scene, String mapFilename) {
    super(scene, new DVector2(0, 0));
    load(mapFilename);
    for (int i = 0; i < mapData.length; i++) {
      for (int j = 0; j < mapData[i].length; j++) {
        if (mapData[i][j] == 0)
          continue;
        scene.addGameObject(new Block(scene, this,
            new DVector2(j * Constants.MAPCHIP_SIZE, i * Constants.MAPCHIP_SIZE), mapData[i][j] - 1));
      }
    }
  }

  private void load(String mapFilename) {
    try {
      InputStreamReader isr = new InputStreamReader(
          Thread.currentThread().getContextClassLoader().getResourceAsStream(mapFilename));
      BufferedReader br = new BufferedReader(isr);

      String str = br.readLine();
      String[] cells = str.split(",");
      int w = Integer.parseInt(cells[0]),
          h = Integer.parseInt(cells[1]);
      mapData = new char[h][w];
      for (int i = 0; i < h; i++) {
        str = br.readLine();
        cells = str.split(",");
        for (int j = 0; j < w; j++) {
          mapData[i][j] = (char) Integer.parseInt(cells[j]);
        }
      }
      br.close();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  @Override
  public void update(int dt) {
    return;
  }
}
