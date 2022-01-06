package com.moai.cw.game_object;

import com.moai.cw.interfaces.DebugDrawable;
import com.moai.cw.interfaces.Hittable;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.Color;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.GraphicObject;
import com.moai.cw.util.Rectangle;

public abstract class Hitbox extends GameObject implements Hittable, DebugDrawable {
  private DVector2 size;
  private Color debugColor;

  public Hitbox(Scene scene, GameObject parent, DVector2 position, DVector2 size, Color debugColor) {
    super(scene, parent, position);
    this.size = size;
    this.debugColor = debugColor;
  }

  public Rectangle getRectangle() {
    return new Rectangle(getPosition().x, getPosition().y, size.x, size.y);
  }

  protected DVector2 getSize() {
    return size;
  }

  protected void setSize(DVector2 size) {
    this.size = size;
  }

  @Override
  public GraphicObject debugDraw(DVector2 offset) {
    return new GraphicObject(getPosition().add(offset), size, debugColor);
  }

}
