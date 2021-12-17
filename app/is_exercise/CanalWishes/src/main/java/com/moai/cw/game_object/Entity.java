package com.moai.cw.game_object;

import com.moai.cw.scene.Scene;
import com.moai.cw.util.CPosition;
import com.moai.cw.util.DPosition;
import com.moai.cw.util.GraphicObject;
import com.moai.cw.util.Rectangle;

public abstract class Entity extends GameObject implements Drawable {
  private DPosition scale;
  private char textureIndex;
  private CPosition textureCoordinate, textureSize;
  private boolean visible;

  public Entity(Scene scene, GameObject parent, DPosition position, DPosition scale, CPosition textureCoordinate,
      CPosition textureSize, int textureIndex) {
    super(scene, parent, position);
    this.scale = scale;
    this.textureCoordinate = textureCoordinate;
    this.textureSize = textureSize;
    this.textureIndex = (char) textureIndex;
  }

  @Override
  public final GraphicObject draw() {
    return new GraphicObject(position, scale, textureIndex, textureCoordinate, textureSize);
  };

  protected final void setScale(DPosition scale) {
    this.scale = scale;
  }

  public final void setVisibility(boolean visible) {
    this.visible = visible;
  }

  public final boolean isVisible() {
    if (parent != null && parent instanceof Drawable)
      return ((Drawable) parent).isVisible() && visible;
    return visible;
  }

  public final Rectangle getRectangle() {
    if (parent != null) {
      DPosition absPos = getPosition();
      return new Rectangle(absPos.x, absPos.y, textureSize.x * scale.x, textureSize.y * scale.y);
    }
    return new Rectangle(position.x, position.y, textureSize.x * scale.x, textureSize.y * scale.y);
  }
}
