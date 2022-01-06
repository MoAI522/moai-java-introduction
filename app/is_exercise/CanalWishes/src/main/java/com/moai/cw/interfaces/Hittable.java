package com.moai.cw.interfaces;

import com.moai.cw.game_object.GameObject;
import com.moai.cw.util.Rectangle;

public interface Hittable {
  public Rectangle getRectangle();

  public void onHit(GameObject target);
}
