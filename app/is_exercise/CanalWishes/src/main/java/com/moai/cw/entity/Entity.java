package com.moai.cw.entity;

import com.moai.cw.game_object.GameObject;
import com.moai.cw.interfaces.Drawable;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.GraphicObject;
import com.moai.cw.util.Rectangle;

public abstract class Entity extends GameObject implements Drawable {
  private DVector2 scale;
  private char textureIndex;
  private CVector2 textureCoordinate, textureSize;
  private boolean visible;

  public Entity(Scene scene, GameObject parent, DVector2 position, DVector2 scale, CVector2 textureCoordinate,
      CVector2 textureSize, int textureIndex) {
    super(scene, parent, position);
    this.scale = scale;
    this.textureCoordinate = textureCoordinate;
    this.textureSize = textureSize;
    this.textureIndex = (char) textureIndex;
  }

  @Override
  public final GraphicObject draw(DVector2 offset) {
    return new GraphicObject(getPosition().add(offset), scale, textureIndex, textureCoordinate, textureSize);
  };

  protected final void setScale(DVector2 scale) {
    this.scale = scale;
  }

  public final void setVisibility(boolean visible) {
    this.visible = visible;
  }

  public final boolean isVisible() {
    if (getParent() != null && getParent() instanceof Drawable)
      return ((Drawable) getParent()).isVisible() && visible;
    return visible;
  }

  public final DVector2 getSize() {
    return new DVector2(textureSize.x * scale.x, textureSize.y * scale.y);
  }

  public final Rectangle getRectangle() {
    if (getParent() != null) {

      DVector2 absPos = getPosition();
      return new Rectangle(absPos.x, absPos.y, textureSize.x * scale.x, textureSize.y * scale.y);
    }
    return new Rectangle(getPosition().x, getPosition().y, textureSize.x * scale.x, textureSize.y * scale.y);
  }
}
