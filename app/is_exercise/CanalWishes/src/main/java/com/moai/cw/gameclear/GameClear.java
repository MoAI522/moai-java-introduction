package com.moai.cw.gameclear;

import com.moai.cw.game_object.GameObject;
import com.moai.cw.scene.Scene;
import com.moai.cw.scene.TitleScene;
import com.moai.cw.util.DVector2;

public class GameClear extends GameObject {
  private static final int DURATION = 120;

  private int count;

  public GameClear(Scene scene) {
    super(scene, new DVector2(0, 0));
    count = 0;
  }

  @Override
  public void update(int dt) {
    if (count == DURATION) {
      getScene().getApp().setScene(new TitleScene(getScene().getApp()));
    }
    count++;
  }

}
