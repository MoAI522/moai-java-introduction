package com.moai.cw.ui;

import com.moai.cw.Constants;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.GraphicObject;

public class AchievedCredit extends UI {
  private int index;
  private boolean achieved;

  public AchievedCredit(Scene scene, GameObject parent, DVector2 position, int index) {
    super(scene, parent, position);
    this.index = index;
    this.achieved = false;
  }

  @Override
  public GraphicObject draw(DVector2 offset) {
    return new GraphicObject(getPosition().add(offset), new DVector2(1, 1), 0,
        new CVector2(0 + index * 12, achieved ? 24 : 0),
        achieved ? new CVector2(12, 12) : new CVector2(1, 1), false);
  }

  @Override
  public int getLayer() {
    return Constants.LAYER_UI;
  }

  @Override
  public void update(int dt) {
  }

  public void setIsAchieved() {
    achieved = true;
  }
}
