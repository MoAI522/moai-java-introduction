package com.moai.cw.title;

import com.moai.cw.KeyInputManager.KeyCode;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.scene.FieldScene;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.DVector2;

public class Title extends GameObject {

  public Title(Scene scene, DVector2 position) {
    super(scene, position);
  }

  @Override
  public void update(int dt) {
    if (getScene().getApp().getKeyInputManager().getState(KeyCode.SPACE) >= 1) {
      getScene().getApp().setScene(new FieldScene(getScene().getApp()));
    }
  }

}
