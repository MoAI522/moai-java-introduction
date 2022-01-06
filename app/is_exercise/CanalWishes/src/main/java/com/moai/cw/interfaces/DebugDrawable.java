package com.moai.cw.interfaces;

import com.moai.cw.util.DVector2;
import com.moai.cw.util.GraphicObject;
import com.moai.cw.util.Rectangle;

public interface DebugDrawable {
  public GraphicObject debugDraw(DVector2 offset);

  public Rectangle getRectangle();
}
