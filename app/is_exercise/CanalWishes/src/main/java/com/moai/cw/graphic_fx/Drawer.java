package com.moai.cw.graphic_fx;

import java.util.ArrayList;

import com.moai.cw.util.GraphicObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Drawer {
  private Image[] textures;

  public Drawer() {
    loadTextures();
  }

  public void loadTextures() {
    int textureNumber = 1;
    textures = new Image[textureNumber];
    for (int i = 0; i < textureNumber; i++) {
      String path = Thread.currentThread().getContextClassLoader().getResource("textures/" + i + ".bmp").getPath();
      textures[i] = new Image("file://" + path);
    }
  }

  public void drawObjects(ArrayList<GraphicObject> objects, GraphicsContext gc) {
    // gc.clearRect(0, 0, 512, 448);
    for (GraphicObject obj : objects) {
      gc.drawImage(textures[obj.textureIndex], obj.uv.x, obj.uv.y, obj.size.x, obj.size.y, obj.position.x,
          obj.position.y, obj.size.x * obj.scale.x, obj.size.y * obj.scale.y);
    }
  }
}
