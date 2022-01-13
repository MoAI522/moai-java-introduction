package com.moai.cw.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.moai.cw.App;
import com.moai.cw.game_object.Camera;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.interfaces.DebugDrawable;
import com.moai.cw.interfaces.Drawable;
import com.moai.cw.util.GraphicObject;

public abstract class Scene {
  protected App app;
  private HashMap<Integer, GameObject> gameObjects, gameObjectsToAdd;
  private ArrayList<Integer> keysToDelete;
  private int objectKeyCount;
  private ArrayList<Camera> cameras;

  public Scene(App app) {
    this.app = app;
    gameObjects = new HashMap<Integer, GameObject>();
    gameObjectsToAdd = new HashMap<Integer, GameObject>();
    keysToDelete = new ArrayList<Integer>();
    objectKeyCount = 0;
    cameras = new ArrayList<Camera>();
  }

  public final void update(int dt) {
    for (int key : gameObjects.keySet()) {
      gameObjects.get(key).update(dt);
    }

    for (int key : gameObjectsToAdd.keySet()) {
      gameObjects.put(key, gameObjectsToAdd.get(key));
    }
    for (int key : keysToDelete) {
      gameObjects.remove(key);
    }
    gameObjectsToAdd.clear();
    keysToDelete.clear();
  }

  public final ArrayList<GraphicObject> draw() {
    ArrayList<GraphicObject> graphicObjects = new ArrayList<GraphicObject>();
    for (Camera camera : cameras) {
      for (GameObject obj : camera.culling()) {
        if (obj instanceof Drawable)
          graphicObjects.add(((Drawable) obj).draw(camera.getPosition().negate()));
        if (obj instanceof DebugDrawable)
          graphicObjects.add(((DebugDrawable) obj).debugDraw(camera.getPosition().negate()));
      }
    }
    return graphicObjects;
  }

  public final int addGameObject(GameObject gameObject) {
    gameObjectsToAdd.put(objectKeyCount, gameObject);
    objectKeyCount++;
    return objectKeyCount - 1;
  }

  public final void removeGameObject(int key) {
    keysToDelete.add(key);
  }

  protected final void addCamera(Camera camera) {
    cameras.add(camera);
    Collections.sort(cameras, Comparator.comparing(Camera::getZindex));
  }

  protected final void removeCamera(Camera camera) {
    cameras.remove(camera);
    Collections.sort(cameras, Comparator.comparing(Camera::getZindex));
  }

  public final HashMap<Integer, GameObject> getGameObjects() {
    return gameObjects;
  }

  public final App getApp() {
    return app;
  }
}
