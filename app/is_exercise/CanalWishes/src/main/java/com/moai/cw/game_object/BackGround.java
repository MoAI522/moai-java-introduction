package com.moai.cw.game_object;

import com.moai.cw.Constants;
import com.moai.cw.interfaces.Drawable;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.GraphicObject;
import com.moai.cw.util.Rectangle;

public class BackGround extends GameObject implements Drawable {
  private int index;

  public BackGround(Scene scene) {
    super(scene, new DVector2(0, 0));
    index = 0;
  }

  @Override
  public GraphicObject draw(DVector2 offset) {
    return new GraphicObject(getPosition().add(offset), new DVector2(4, 4), 4,
        new CVector2((index % 4) * 64, (index / 4) * 48), new CVector2(64, 48), false);
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
    return Constants.LAYER_BACKGROUND;
  }

  @Override
  public void update(int dt) {
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
