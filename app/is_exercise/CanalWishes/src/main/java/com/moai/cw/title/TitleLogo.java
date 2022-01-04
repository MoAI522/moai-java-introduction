package com.moai.cw.title;

import com.moai.cw.game_object.GameObject;
import com.moai.cw.interfaces.Drawable;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.GraphicObject;
import com.moai.cw.util.Rectangle;

public class TitleLogo extends GameObject implements Drawable {

  public TitleLogo(Scene scene, DVector2 position) {
    super(scene, position);
  }

  @Override
  public GraphicObject draw(DVector2 offset) {
    return new GraphicObject(getPosition(), new DVector2(1, 1), 3, new CVector2(0, 0), new CVector2(204, 79));
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
    return 1;
  }

  @Override
  public void update(int dt) {
  }

}
