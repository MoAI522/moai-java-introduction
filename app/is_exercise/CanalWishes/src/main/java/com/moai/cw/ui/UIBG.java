package com.moai.cw.ui;

import com.moai.cw.Constants;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.GraphicObject;

public class UIBG extends UI {

  public UIBG(Scene scene) {
    super(scene, null, new DVector2(0, Constants.DISPLAY_HEIGHT - Constants.STATUS_HEIGHT));
  }

  @Override
  public GraphicObject draw(DVector2 offset) {
    GraphicObject background = new GraphicObject(getPosition(), new DVector2(1, 1), 3, new CVector2(0, 98),
        new CVector2(Constants.DISPLAY_WIDTH, Constants.STATUS_HEIGHT), false);
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
