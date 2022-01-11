package com.moai.cw.game_object;

import com.moai.cw.interfaces.EventArea;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Rectangle;

public class EventAreaManager extends GameObject {

  public EventAreaManager(Scene scene) {
    super(scene, new DVector2(0, 0));
  }

  @Override
  public void update(int dt) {

  }

  public EventArea checkArea(Rectangle rect) {
    for (int key : getScene().getGameObjects().keySet()) {
      GameObject object = getScene().getGameObjects().get(key);
      if (!(object instanceof EventArea))
        continue;
      if (!Rectangle.intersect(((EventArea) object).getRectangle(), rect))
        continue;
      return (EventArea) object;
    }
    return null;
  }
}
