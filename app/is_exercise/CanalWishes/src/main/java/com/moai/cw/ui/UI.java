package com.moai.cw.ui;

import com.moai.cw.game_object.GameObject;
import com.moai.cw.interfaces.Drawable;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Rectangle;

public abstract class UI extends GameObject implements Drawable {
  private boolean visibility;

  public UI(Scene scene, GameObject parent, DVector2 position) {
    super(scene, parent, position);
    visibility = true;
  }

  @Override
  public void setVisibility(boolean visibility) {
    this.visibility = visibility;
  }

  @Override
  public boolean isVisible() {
    return visibility;
  }

  @Override
  public Rectangle getRectangle() {
    return null;
  }
}
