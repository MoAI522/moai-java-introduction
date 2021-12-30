package com.moai.cw.ui;

import com.moai.cw.Constants;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.interfaces.Drawable;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.Color;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.GraphicObject;
import com.moai.cw.util.Rectangle;

public class UI extends GameObject implements Drawable {
  private boolean visibility;

  public UI(Scene scene) {
    super(scene, new DVector2(0, Constants.DISPLAY_HEIGHT - Constants.STATUS_HEIGHT));
    visibility = true;
  }

  @Override
  public void update(int dt) {

  }

  @Override
  public GraphicObject draw(DVector2 offset) {
    GraphicObject background = new GraphicObject(getPosition(),
        new DVector2(Constants.DISPLAY_WIDTH, Constants.STATUS_HEIGHT), new Color(0, 0, 0));
    return background;
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

  @Override
  public int getLayer() {
    return Constants.LAYER_UI_BACKGROUND;
  }
}
