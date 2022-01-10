package com.moai.cw.ui;

import com.moai.cw.Constants;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.Color;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.GraphicObject;

public class UIBG extends UI {

  public UIBG(Scene scene) {
    super(scene, null, new DVector2(0, Constants.DISPLAY_HEIGHT - Constants.STATUS_HEIGHT));
  }

  @Override
  public GraphicObject draw(DVector2 offset) {
    GraphicObject background = new GraphicObject(getPosition(),
        new DVector2(Constants.DISPLAY_WIDTH, Constants.STATUS_HEIGHT), new Color(0, 0, 0));
    return background;
  }

  @Override
  public void update(int dt) {

  }

  @Override
  public int getLayer() {
    return Constants.LAYER_UI_BACKGROUND;
  }
}
