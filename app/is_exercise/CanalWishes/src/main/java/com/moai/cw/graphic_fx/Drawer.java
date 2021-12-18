package com.moai.cw.graphic_fx;

import java.io.File;
import java.util.ArrayList;

import com.moai.cw.Constants;
import com.moai.cw.util.GraphicObject;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;

public class Drawer {
  private Image[] textures;

  public Drawer() {
    loadTextures();
  }

  public void loadTextures() {
    int textureNumber = 1;
    textures = new Image[textureNumber];
    for (int i = 0; i < textureNumber; i++) {
      String path = Thread.currentThread().getContextClassLoader().getResource("textures/" + i + ".png").getPath();
      textures[i] = new Image(new File(path).toURI().toString());
    }
  }

  public void drawObjects(ArrayList<GraphicObject> objects, PixelWriter pw) {
    for (GraphicObject obj : objects) {
      PixelReader pr = textures[obj.textureIndex].getPixelReader();
      int originX = (int) Math.floor(obj.position.x),
          originY = (int) Math.floor(obj.position.y),
          pixelRatio = Constants.PIXEL_RATIO,
          uTo = obj.uv.x + obj.size.x,
          vTo = obj.uv.y + obj.size.y,
          dispW = Constants.DISPLAY_WIDTH, dispH = Constants.DISPLAY_HEIGHT;
      for (int v = obj.uv.y, i = 0; v < vTo; v++, i++) {
        for (int u = obj.uv.x, j = 0; u < uTo; u++, j++) {
          if (originX + j < 0 || originX + j >= dispW || originY + i < 0 || originY + i >= dispH)
            continue;
          for (int k = 0; k < pixelRatio; k++) {
            for (int l = 0; l < pixelRatio; l++) {
              pw.setPixels((originX + j) * pixelRatio + l, (originY + i) * pixelRatio + k,
                  1, 1, pr, u, v);
            }
          }
        }
      }
    }
  }
}
