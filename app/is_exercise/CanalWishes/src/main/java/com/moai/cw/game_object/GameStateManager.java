package com.moai.cw.game_object;

import com.moai.cw.scene.FieldScene;
import com.moai.cw.scene.GameClearScene;
import com.moai.cw.scene.GameOverScene;
import com.moai.cw.store.FieldStore;
import com.moai.cw.util.DVector2;

public class GameStateManager extends GameObject {
  private static final int SLEEP_DURATION = 60;

  private FieldStore store;
  private int sleepCount;

  public GameStateManager(FieldScene scene) {
    super(scene, new DVector2(0, 0));
    store = scene.getStore();
    sleepCount = 0;
  }

  @Override
  public void update(int dt) {
    if (store.isGameClear()) {
      if (sleepCount == SLEEP_DURATION) {
        getScene().getApp().setScene(new GameClearScene(getScene().getApp()));
      }
      sleepCount++;
    } else if (store.isGameOver()) {
      if (sleepCount == SLEEP_DURATION) {
        getScene().getApp().setScene(new GameOverScene(getScene().getApp()));
      }
      sleepCount++;
    }
  }
}
