package com.moai.cw.title;

import com.moai.cw.Constants;
import com.moai.cw.KeyInputManager.KeyCode;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.interfaces.Drawable;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.GraphicObject;
import com.moai.cw.util.Rectangle;

public class TitleBG extends GameObject implements Drawable {
  private static final double SCROLL_SPEED = 0.05;

  private double scroll;

  public TitleBG(Scene scene, DVector2 position) {
    super(scene, position);
    scroll = 0;
  }

  @Override
  public void update(int dt) {
    scroll = (scroll + dt * SCROLL_SPEED);
  }

  @Override
  public GraphicObject draw(DVector2 offset) {
    return new GraphicObject(new DVector2(0, 0), new DVector2(1, 1), 2, new CVector2(0, (int) scroll),
        new CVector2(Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT), new CVector2(0, 0),
        new CVector2(Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT));
  }

  @Override
  public void setVisibility(boolean visibility) {

  }

  @Override
  public boolean isVisible() {
    return true;
  }

  @Override
  public Rectangle getRectangle() {
    return null;
  }

  @Override
  public int getLayer() {
    return 0;
  }

}
