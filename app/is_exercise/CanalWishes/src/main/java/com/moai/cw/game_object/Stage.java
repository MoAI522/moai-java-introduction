package com.moai.cw.game_object;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import com.moai.cw.scene.Scene;
import com.moai.cw.util.DPosition;

public class Stage extends GameObject {
  private char[][] mapData;

  public Stage(Scene scene, String mapFilename) {
    super(scene, null);
    load(mapFilename);
    for (int i = 0; i < mapData.length; i++) {
      for (int j = 0; j < mapData[i].length; j++) {
        if (mapData[i][j] == 0)
          continue;
        scene.addGameObject(new Block(scene, this, new DPosition(j * 32, i * 32), mapData[i][j] - 1));
      }
    }
  }

  private void load(String mapFilename) {
    File f = new File(mapFilename);
    try {
      Reader fr = new FileReader(f);
      BufferedReader br = new BufferedReader(fr);

      String str = br.readLine();
      String[] cells = str.split(",");
      int w = Integer.parseInt(cells[0]),
          h = Integer.parseInt(cells[1]);
      mapData = new char[h][w];
      for (int i = 0; i < h; i++) {
        str = br.readLine();
        for (int j = 0; j < w; j++) {
          mapData[i][j] = (char) Integer.parseInt(cells[j]);
        }
      }
      br.close();
    } catch (Exception e) {

    }
  }

  @Override
  public void update(double dt) {
    return;
  }
}
