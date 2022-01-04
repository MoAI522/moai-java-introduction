package com.moai.cw.fireball;

import com.moai.cw.Constants;
import com.moai.cw.entity.Entity;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Direction.DIRECTION;

public class Bress extends Entity {
  private static final int LIFETIME = 10;
  private static final double DISTANCE = 50;
  public static final int SIZE = 12;

  private int direction;
  private int frame;
  private DVector2 originPos;

  public Bress(Scene scene, DVector2 position, DIRECTION direction) {
    super(scene, null, position, new DVector2(1, 1), new CVector2(24, 0), new CVector2(SIZE, SIZE), 0);
    this.direction = direction == DIRECTION.LEFT ? -1 : 1;
    this.frame = 0;
    this.originPos = getPosition();
  }

  @Override
  public int getLayer() {
    return Constants.LAYER_FIREBALL;
  }

  @Override
  public void update(int dt) {
    setPosition(originPos.add(new DVector2(Math.sin(Math.PI / 2 / LIFETIME * frame) * direction * DISTANCE, 0)));

    frame++;
    if (frame == LIFETIME) {
      destroy();
    }
  }

}
