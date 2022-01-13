package com.moai.cw.ui;

import com.moai.cw.Constants;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.GraphicObject;

public class AchievedCredit extends UI {
  public AchievedCredit(Scene scene, GameObject parent, DVector2 position) {
    super(scene, parent, position);
  }

  @Override
  public GraphicObject draw(DVector2 offset) {
    return new GraphicObject(getPosition().add(offset), new DVector2(1, 1), 3,
        new CVector2(145, 84),
        new CVector2(14, 14), false);
  }

  @Override
  public int getLayer() {
    return Constants.LAYER_UI;
  }

  @Override
  public void update(int dt) {
  }
}
