package com.moai.cw.fireball;

import com.moai.cw.Constants;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Direction.DIRECTION;

public class Saliva extends Fireball {
  private static final int LIFETIME = 10;
  private static final double DISTANCE = 50;
  public static final int SIZE = 12;

  private int frame;
  private DVector2 originPos;

  public Saliva(Scene scene, DVector2 position, DIRECTION direction) {
    super(scene, position, new CVector2(144, 0), new CVector2(SIZE, SIZE), 1, direction, 10);
    this.frame = 0;
    this.originPos = getPosition();
  }

  @Override
  public int getLayer() {
    return Constants.LAYER_FIREBALL;
  }

  @Override
  public void update(int dt) {
    setPosition(originPos.add(new DVector2(Math.sin(Math.PI / 2 / LIFETIME * frame) * getDirection() * DISTANCE, 0)));

    frame++;
    if (frame == LIFETIME) {
      destroy();
    }
  }

}
