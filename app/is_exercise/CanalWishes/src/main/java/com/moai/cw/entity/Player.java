package com.moai.cw.entity;

import com.moai.cw.Constants;
import com.moai.cw.KeyInputManager.KeyCode;
import com.moai.cw.game_object.Entity;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CPosition;
import com.moai.cw.util.DPosition;

public class Player extends Entity {
  private enum State {
    STOP, WALK, SPRINT, JUMP, FALL, HOVER
  }

  private State state;

  public Player(Scene scene, DPosition position) {
    super(scene, null, position, new DPosition(1, 1), new CPosition(0, 0), new CPosition(24, 24), 1);
    state = State.STOP;
  }

  @Override
  public void update(int dt) {
    DPosition vp = position.clone();
    if (scene.getApp().getKeyInputManager().getState(KeyCode.A) > 0) {
      vp.set(vp.x - 0.1 * dt, vp.y);
    }
    if (scene.getApp().getKeyInputManager().getState(KeyCode.D) > 0) {
      vp.set(vp.x + 0.1 * dt, vp.y);
    }

    position = vp;
  }
}
