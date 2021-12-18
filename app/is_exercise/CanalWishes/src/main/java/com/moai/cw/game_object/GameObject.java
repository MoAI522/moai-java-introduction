package com.moai.cw.game_object;

import java.util.ArrayList;

import com.moai.cw.scene.Scene;
import com.moai.cw.util.DPosition;

public abstract class GameObject {
  protected Scene scene;
  private int objectKey;
  protected GameObject parent;
  protected ArrayList<GameObject> children;
  protected DPosition position;

  public GameObject(Scene scene, DPosition position) {
    this.scene = scene;
    objectKey = scene.addGameObject(this);
    this.position = position;
    this.children = new ArrayList<GameObject>();
  }

  public GameObject(Scene scene, GameObject parent, DPosition position) {
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

  public final void setPosition(DPosition position) {
    this.position = position;
  }

  public final DPosition getPosition() {
    if (parent != null)
      return parent.getPosition().add(position);
    return position;
  }
}
