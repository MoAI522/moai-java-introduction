package com.moai.cw.game_object;

import java.util.ArrayList;
import java.util.HashMap;

import com.moai.cw.Constants;
import com.moai.cw.entity.Entity;
import com.moai.cw.interfaces.DebugDrawable;
import com.moai.cw.interfaces.Drawable;
import com.moai.cw.interfaces.OffScreenListener;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Rectangle;

public class Camera extends GameObject {
  private Rectangle fov;
  private Entity target;
  private DVector2 fovSize;
  private Rectangle range;
  private int[] layers;
  private int zindex;

  public Camera(Scene scene, int[] layers, int zindex, Entity target, DVector2 fovSize, Rectangle range) {
    super(scene, fovSize.multipleScalar(0.5));
    this.layers = layers;
    this.zindex = zindex;
    this.target = target;
    this.fovSize = fovSize;
    this.range = range;
  }

  public Camera(Scene scene, int[] layers, int zindex) {
    super(scene, new DVector2(0, 0));
    this.layers = layers;
    this.zindex = zindex;
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
    HashMap<Integer, GameObject> gameObjects = getScene().getGameObjects();
    ArrayList<ArrayList<GameObject>> objectsToDraw = new ArrayList<ArrayList<GameObject>>();
    for (int i = 0; i < layers.length; i++) {
      objectsToDraw.add(new ArrayList<GameObject>());
    }
    for (int key : gameObjects.keySet()) {
      GameObject obj = gameObjects.get(key);
      if (obj instanceof Drawable && ((Drawable) obj).isVisible()) {
        int layer = ((Drawable) obj).getLayer();
        Rectangle rect = ((Drawable) obj).getRectangle();
        int i = judge(layer, rect);
        if (i >= 0)
          objectsToDraw.get(i).add(obj);
      }
      if (Constants.DEBUG && obj instanceof DebugDrawable) {
        int layer = Constants.LAYER_DEBUG;
        Rectangle rect = null;
        int i = judge(layer, rect);
        if (i >= 0)
          objectsToDraw.get(i).add(obj);
      }
      if (obj instanceof OffScreenListener) {
        Rectangle rect = ((OffScreenListener) obj).getRectangle();
        if (rect == null || fov == null) {
        } else if (Rectangle.intersect(fov, rect)) {
          if (obj instanceof Spawner)
            ((Spawner) obj).onOnScreen();
        } else {
          ((OffScreenListener) obj).onOffScreen();
        }
      }
    }

    ArrayList<GameObject> result = new ArrayList<GameObject>();
    for (ArrayList<GameObject> layer : objectsToDraw) {
      for (GameObject object : layer) {
        result.add(object);
      }
    }

    return result;
  }

  private int judge(int layer, Rectangle rect) {
    int i;
    for (i = 0; i < layers.length; i++) {
      if (layer == layers[i])
        break;
    }
    if (i == layers.length)
      return -1;

    if (rect == null || fov == null) {
      return i;
    }
    if (Rectangle.intersect(fov, rect)) {
      return i;
    }
    return -2;
  }

  public int getZindex() {
    return zindex;
  }
}
