package com.moai.cw.game_object;

import java.util.ArrayList;
import java.util.HashMap;

import com.moai.cw.scene.Scene;
import com.moai.cw.util.DPosition;
import com.moai.cw.util.Rectangle;

public class Camera extends GameObject {
  private Rectangle fov;

  public Camera(Scene scene) {
    super(scene, new DPosition(128, 112));
  }

  @Override
  public void update(double dt) {
    int w = 256, h = 224;
    fov = new Rectangle(position.x - w / 2, position.y - h / 2, w, h);
  }

  public ArrayList<GameObject> culling() {
    HashMap<Integer, GameObject> gameObjects = scene.getGameObjects();
    ArrayList<GameObject> objectsToDraw = new ArrayList<GameObject>();
    for (int key : gameObjects.keySet()) {
      GameObject obj = gameObjects.get(key);
      if (!(obj instanceof Drawable))
        continue;
      if (Rectangle.intersect(fov, ((Drawable) obj).getRectangle()))
        objectsToDraw.add(obj);
    }
    return objectsToDraw;
  }
}
