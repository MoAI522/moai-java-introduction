package com.moai.cw.ui;

import com.moai.cw.Constants;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.scene.Scene;
import com.moai.cw.store.FieldStore;
import com.moai.cw.util.DVector2;

public class FieldUI extends GameObject {
  private FieldStore store;
  private HPBar hpbar;
  private AchievedCredit[] credits;

  public FieldUI(Scene scene, FieldStore store) {
    super(scene, new DVector2(0, 0));
    this.store = store;

    UIBG uibg = new UIBG(scene);
    hpbar = new HPBar(scene, uibg, new DVector2(15, 23), new DVector2(100, 15));
    credits = new AchievedCredit[Constants.CREDITS_NUMBER];
    for (int i = 0; i < credits.length; i++) {
      credits[i] = new AchievedCredit(scene, uibg, new DVector2(128 + 14 * (i % 8), 23 + 14 * (i / 8)));
    }
  }

  @Override
  public void update(int dt) {
    hpbar.setRatio((double) store.getPlayerHP() / Constants.PLAYER_HP_MAX);
    for (int i = 0; i < credits.length; i++) {
      credits[i].setVisibility(store.hasCredit(i));
    }
  }
}
