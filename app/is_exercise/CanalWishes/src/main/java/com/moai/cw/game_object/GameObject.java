package com.moai.cw.game_object;

import java.util.ArrayList;

import com.moai.cw.scene.Scene;
import com.moai.cw.util.DVector2;

public abstract class GameObject {
  private Scene scene;
  private int objectKey;
  private GameObject parent;
  private ArrayList<GameObject> children;
  private DVector2 position;

  public GameObject(Scene scene, DVector2 position) {
    this.scene = scene;
    objectKey = scene.addGameObject(this);
    this.position = position;
    this.children = new ArrayList<GameObject>();
  }

  public GameObject(Scene scene, GameObject parent, DVector2 position) {
    this(scene, position);
    this.parent = parent;
  }

  public abstract void update(int dt);

  public final void destroy() {
    for (GameObject child : children) {
      child.destroy();
    }
    scene.removeGameObject(objectKey);
  }

  public final void appendChild(GameObject child) {
    children.add(child);
  }

  public final void setPosition(DVector2 position) {
    this.position = position;
  }

  public final DVector2 getPosition() {
    if (parent != null)
      return parent.getPosition().add(position);
    return position;
  }

  protected Scene getScene() {
    return scene;
  }

  protected GameObject getParent() {
    return parent;
  }
}
