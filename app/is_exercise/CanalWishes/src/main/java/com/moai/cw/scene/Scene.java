package com.moai.cw.scene;

import java.util.ArrayList;
import java.util.HashMap;

import com.moai.cw.App;
import com.moai.cw.game_object.Camera;
import com.moai.cw.game_object.Drawable;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.util.GraphicObject;

public abstract class Scene {
  protected App app;
  private HashMap<Integer, GameObject> gameObjects;
  private int objectKeyCount;
  private ArrayList<Camera> cameras;

  public Scene(App app) {
    this.app = app;
    gameObjects = new HashMap<Integer, GameObject>();
    objectKeyCount = 0;
    cameras = new ArrayList<Camera>();
  }

  public abstract void update(double dt);

  public final ArrayList<GraphicObject> draw() {
    ArrayList<GraphicObject> graphicObjects = new ArrayList<GraphicObject>();
    for (int key : gameObjects.keySet()) {
      GameObject obj = gameObjects.get(key);
      if (!(obj instanceof Drawable))
        continue;
      graphicObjects.add(((Drawable) obj).draw());
    }
    return graphicObjects;
  }

  public final int addGameObject(GameObject gameObject) {
    gameObjects.put(objectKeyCount, gameObject);
    objectKeyCount++;
    return objectKeyCount - 1;
  }

  public final void removeGameObject(int key) {
    gameObjects.remove(key);
  }

  protected final void addCamera(Camera camera) {
    cameras.add(camera);
  }

  public final HashMap<Integer, GameObject> getGameObjects() {
    return gameObjects;
  }
}
