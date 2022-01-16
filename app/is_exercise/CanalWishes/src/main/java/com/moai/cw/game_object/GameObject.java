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
  private boolean disposed;

  public GameObject(Scene scene, DVector2 position) {
    this.scene = scene;
    objectKey = scene.addGameObject(this);
    this.position = position;
    this.children = new ArrayList<GameObject>();
    this.disposed = false;
  }

  public GameObject(Scene scene, GameObject parent, DVector2 position) {
    this(scene, position);
    this.parent = parent;
    if (parent != null)
      parent.appendChild(this);
  }

  public abstract void update(int dt);

  public final void appendChild(GameObject child) {
    children.add(child);
  }

  public final void destroy() {
    clearChildren();
    scene.removeGameObject(objectKey);
    disposed = true;
  }

  public final void clearChildren() {
    for (GameObject child : children) {
      child.destroy();
    }
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

  public boolean isDisposed() {
    return disposed;
  }

  public String toString() {
    return "GameObject(" + this.getClass() + ") position: {rel: " + position + " abs: " + getPosition() + "}";
  }
}
