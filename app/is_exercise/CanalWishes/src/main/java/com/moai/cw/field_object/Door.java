package com.moai.cw.field_object;

import java.util.List;

import com.moai.cw.Constants;
import com.moai.cw.entity.Entity;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.interfaces.EventArea;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.IVector2;

public class Door extends Entity implements EventArea {
  private static final int HEIGHT = 36;
  private static final int OFFSET_Y = 12;

  private String link;

  public Door(Scene scene, GameObject parent, IVector2 position, List<String> params) {
    super(scene, parent,
        new DVector2(position.x * Constants.MAPCHIP_SIZE, position.y * Constants.MAPCHIP_SIZE + OFFSET_Y),
        new DVector2(1, 1), new CVector2(0, 0), new CVector2(Constants.MAPCHIP_SIZE, HEIGHT), 0);
    link = params.get(0);
  }

  @Override
  public int getLayer() {
    return Constants.LAYER_FIELDOBJECT;
  }

  @Override
  public void update(int dt) {

  }

  public String getLink() {
    return link;
  }
}