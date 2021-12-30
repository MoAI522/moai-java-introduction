package com.moai.cw.entity;

import com.moai.cw.Constants;
import com.moai.cw.KeyInputManager.KeyCode;
import com.moai.cw.game_object.Entity;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;

public class Player extends Rigitbody {
  private static double JUMP_F = 0.2;
  private static double WALK_SPEED = 1;

  private enum State {
    STOP, WALK, SPRINT, JUMP, FALL, HOVER
  }

  private State state;

  public Player(Scene scene, DVector2 position) {
    super(scene, null, position, new DVector2(1, 1), new CVector2(0, 0), new CVector2(24, 24), 1, 1, 1);
    state = State.STOP;
  }

  @Override
  public void update(int dt) {
    DVector2 nextVelocity = getVelocity();
    nextVelocity.x = 0;
    if (scene.getApp().getKeyInputManager().getState(KeyCode.A) > 0) {
      nextVelocity.x = -WALK_SPEED;
    }
    if (scene.getApp().getKeyInputManager().getState(KeyCode.D) > 0) {
      nextVelocity.x = WALK_SPEED;
    }
    setVelocity(nextVelocity);

    if (scene.getApp().getKeyInputManager().getState(KeyCode.SPACE) == 1) {
      addForce(new DVector2(0, -JUMP_F));
    }

    physics(dt);
    scene.getApp().setDebugInfo("player-pos", getPosition().toString());
  }
}
