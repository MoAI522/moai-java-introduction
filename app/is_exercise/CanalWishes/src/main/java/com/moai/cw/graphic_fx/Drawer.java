package com.moai.cw.graphic_fx;

import java.io.File;
import java.util.ArrayList;

import com.moai.cw.util.GraphicObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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

  public void drawObjects(ArrayList<GraphicObject> objects, GraphicsContext gc) {
    for (GraphicObject obj : objects) {
      gc.setFill(Color.BLUE);
      gc.fillRect(obj.position.x * 2,
          obj.position.y * 2, obj.size.x * obj.scale.x * 2, obj.size.y * obj.scale.y * 2);
      gc.drawImage(textures[obj.textureIndex], obj.uv.x, obj.uv.y, obj.size.x, obj.size.y, obj.position.x
          * 2,
          obj.position.y * 2, obj.size.x * obj.scale.x * 2, obj.size.y * obj.scale.y * 2);
    }
  }
}
