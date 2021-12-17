package com.moai.cw.game_object;

import java.util.ArrayList;
import java.util.HashMap;

import com.moai.cw.Constants;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.DPosition;
import com.moai.cw.util.Rectangle;

public class Camera extends GameObject {
  private Rectangle fov;

  public Camera(Scene scene) {
    super(scene, new DPosition(256, 300));
  }

  @Override
  public void update(double dt) {

    fov = new Rectangle(position.x - Constants.DISPLAY_WIDTH / 2,
        position.y - (Constants.DISPLAY_HEIGHT - Constants.STATUS_HEIGHT) / 2,
        Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT - Constants.STATUS_HEIGHT);
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
