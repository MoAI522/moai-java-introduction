package com.moai.cw.game_object;

import java.lang.reflect.Constructor;

import com.moai.cw.Constants;
import com.moai.cw.interfaces.OffScreenListener;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.EnemyData;
import com.moai.cw.util.Rectangle;
import com.moai.cw.util.Direction.DIRECTION;

public class Spawner extends GameObject implements OffScreenListener {
  private EnemyData data;
  private boolean offScreen = true;
  private boolean spawned = false;

  public Spawner(Scene scene, GameObject parent, EnemyData data) {
    super(scene, parent, new DVector2(data.getPosition().get(0) * Constants.MAPCHIP_SIZE,
        data.getPosition().get(1) * Constants.MAPCHIP_SIZE));
    this.data = data;
  }

  @Override
  public void update(int dt) {
    if (!offScreen || spawned)
      return;

    try {
      Class<?> enemyClass = Class.forName(data.getClassName());
      Constructor<?> constructor = enemyClass.getConstructor(Scene.class, Spawner.class, DVector2.class,
          DIRECTION.class, int.class);
      constructor.newInstance(getScene(), this,
          new DVector2(data.getPosition().get(0) * Constants.MAPCHIP_SIZE,
              data.getPosition().get(1) * Constants.MAPCHIP_SIZE),
          (data.getDirection().equals("left") ? DIRECTION.LEFT : DIRECTION.RIGHT), data.getType());
    } catch (Exception e) {
      e.printStackTrace();
    }
    offScreen = false;
    spawned = true;
  }

  @Override
  public void onOffScreen() {
    offScreen = true;
  }

  public void onChildDestroyed() {
    spawned = false;
  }

  @Override
  public Rectangle getRectangle() {
    return new Rectangle(getPosition().x, getPosition().y, Constants.MAPCHIP_SIZE, Constants.MAPCHIP_SIZE);
  }
}
