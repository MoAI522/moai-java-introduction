package com.moai.cw.interfaces;

import com.moai.cw.util.DVector2;
import com.moai.cw.util.GraphicObject;
import com.moai.cw.util.Rectangle;

public interface Drawable {
  public GraphicObject draw(DVector2 offset);

  public void setVisibility(boolean visibility);

  public boolean isVisible();

  public Rectangle getRectangle();

  public int getLayer();
}
