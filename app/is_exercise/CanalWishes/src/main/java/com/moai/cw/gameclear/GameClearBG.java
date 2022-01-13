package com.moai.cw.gameclear;

import com.moai.cw.Constants;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.interfaces.Drawable;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.Color;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.GraphicObject;
import com.moai.cw.util.Rectangle;

public class GameClearBG extends GameObject implements Drawable {

  public GameClearBG(Scene scene) {
    super(scene, new DVector2(0, 0));
  }

  @Override
  public GraphicObject draw(DVector2 offset) {
    return new GraphicObject(getPosition(), new DVector2(Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT),
        new Color(0, 0, 0));
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

  @Override
  public void update(int dt) {

  }

}
