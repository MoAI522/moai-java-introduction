package com.moai.cw.interfaces;

import com.moai.cw.util.DVector2;
import com.moai.cw.util.Rectangle;

public interface OffScreenListener {
  public Rectangle getRectangle();

  public void onOffScreen();
}
