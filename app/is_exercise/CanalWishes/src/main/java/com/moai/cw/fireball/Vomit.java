package com.moai.cw.fireball;

import com.moai.cw.Constants;
import com.moai.cw.interfaces.OffScreenListener;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Direction.DIRECTION;

public class Vomit extends Fireball implements OffScreenListener {
  public static final int SIZE = 24;
  public static final double SPEED = 8;

  private int frame;
  private DVector2 originPos;

  public Vomit(Scene scene, DVector2 position, DIRECTION direction) {
    super(scene, position, new CVector2(144, 24), new CVector2(SIZE, SIZE), 1, direction, 100);
    this.frame = 0;
    this.originPos = getPosition();
  }

  @Override
  public int getLayer() {
    return Constants.LAYER_FIREBALL;
  }

  @Override
  public void update(int dt) {
    setPosition(originPos.add(new DVector2(SPEED * getDirection() * frame, 0)));

    frame++;
  }

  @Override
  public void onOffScreen() {
    destroy();
  }

}
