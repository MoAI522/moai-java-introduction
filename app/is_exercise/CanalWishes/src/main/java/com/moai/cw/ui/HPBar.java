package com.moai.cw.ui;

import com.moai.cw.Constants;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.Color;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.GraphicObject;

public class HPBar extends UI {
  private DVector2 size;
  private double ratio;

  public HPBar(Scene scene, GameObject parent, DVector2 position, DVector2 size) {
    super(scene, parent, position);
    this.size = size;
    this.ratio = 1;
  }

  @Override
  public GraphicObject draw(DVector2 offset) {
    return new GraphicObject(getPosition(), new DVector2(size.x * ratio, size.y), new Color(0, 0.8, 0));
  }

  @Override
  public int getLayer() {
    return Constants.LAYER_UI;
  }

  @Override
  public void update(int dt) {

  }

  public void setRatio(double ratio) {
    this.ratio = ratio;
  }
}
