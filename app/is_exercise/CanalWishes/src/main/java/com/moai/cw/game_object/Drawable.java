package com.moai.cw.game_object;

import com.moai.cw.util.GraphicObject;
import com.moai.cw.util.Rectangle;

public interface Drawable {
  public GraphicObject draw();

  public void setVisibility(boolean visibility);

  public boolean isVisible();

  public Rectangle getRectangle();
}
