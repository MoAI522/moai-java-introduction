package com.moai.cw.game_object;

import java.util.ArrayList;
import java.util.HashMap;

import com.moai.cw.Constants;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Rectangle;

public class Camera extends GameObject {
  private Rectangle fov;
  private Entity target;
  private DVector2 fovSize;
  private Rectangle range;
  private int[] layers;

  public Camera(Scene scene, int[] layers, Entity target, DVector2 fovSize, Rectangle range) {
    super(scene, fovSize.multipleScalar(0.5));
    this.layers = layers;
    this.target = target;
    this.fovSize = fovSize;
    this.range = range;
  }

  public Camera(Scene scene, int[] layers) {
    super(scene, new DVector2(0, 0));
    this.layers = layers;
    this.target = null;
    this.fovSize = null;
    this.range = null;
  }

  @Override
  public void update(int dt) {
    if (target == null || fovSize == null || range == null) {
      fov = null;
      return;
    }
    DVector2 newPosition = target.getPosition().add(target.getSize().multipleScalar(0.5))
        .add(fovSize.multipleScalar(0.5).negate());
    if (newPosition.x < range.x0) {
      newPosition.x = range.x0;
    } else if (newPosition.x + fovSize.x > range.x1) {
      newPosition.x = range.x1 - fovSize.x;
    }
    if (newPosition.y < range.y0) {
      newPosition.y = range.y0;
    } else if (newPosition.y + fovSize.y > range.y1) {
      newPosition.y = range.y1 - fovSize.y;
    }
    setPosition(newPosition);
    fov = new Rectangle(getPosition().x, getPosition().y, fovSize.x, fovSize.y);
  }

  public ArrayList<GameObject> culling() {
    HashMap<Integer, GameObject> gameObjects = scene.getGameObjects();
    ArrayList<GameObject> objectsToDraw = new ArrayList<GameObject>();
    for (int key : gameObjects.keySet()) {
      GameObject obj = gameObjects.get(key);
      if (!(obj instanceof Drawable))
        continue;
      int i;
      for (i = 0; i < layers.length; i++) {
        if (((Drawable) obj).getLayer() == layers[i])
          break;
      }
      if (i == layers.length)
        continue;
      Rectangle rect = ((Drawable) obj).getRectangle();
      if (rect == null || fov == null) {
        objectsToDraw.add(obj);
        continue;
      }
      if (Rectangle.intersect(fov, rect))
        objectsToDraw.add(obj);
    }
    return objectsToDraw;
  }
}
