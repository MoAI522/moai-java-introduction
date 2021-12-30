package com.moai.cw.entity;

import com.moai.cw.game_object.Entity;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Rectangle;

public abstract class Rigitbody extends Entity {
  private final double g = 0.02;

  private double mass;
  private double drag;

  private DVector2 velocity;
  private DVector2 currentForce;
  private boolean airborne;

  public Rigitbody(Scene scene, GameObject parent, DVector2 position, DVector2 scale, CVector2 textureCoordinate,
      CVector2 textureSize, int textureIndex, double mass, double drag) {
    super(scene, parent, position, scale, textureCoordinate, textureSize, textureIndex);
    velocity = new DVector2(0, 0);
    currentForce = new DVector2(0, mass * g);
    this.mass = mass;
    this.drag = drag;
    airborne = false;
  }

  protected final void addForce(DVector2 force) {
    currentForce = currentForce.add(force);
  }

  protected final void physics(int dt) {
    airborne = true;

    DVector2 dp = velocity.add(currentForce.multipleScalar(1 / drag).negate())
        .multipleScalar((mass / drag) * (1 - Math.exp(-drag / mass * dt))).add(currentForce.multipleScalar(dt / drag));

    {
      Rectangle rect = new Rectangle(getPosition().x + dp.x, getPosition().y,
          getSize().x, getSize().y);
      double r = 1;
      for (int i : getScene().getGameObjects().keySet()) {
        GameObject gameObject = getScene().getGameObjects().get(i);
        if (!(gameObject instanceof Block))
          continue;
        Block block = (Block) gameObject;
        if (!Rectangle.intersect(rect, block.getRectangle()))
          continue;
        double tr = 1;
        if (dp.x > 0) {
          tr = (block.getPosition().x - getRectangle().x1) / dp.x;
          if (tr < 0)
            tr = 1;
        } else if (dp.x == 0) {
          tr = 1;
        } else {
          tr = (getPosition().x - block.getRectangle().x1) / -dp.x;
          if (tr < 0)
            tr = 1;
        }
        tr = Math.min(tr, 1);
        r = Math.min(tr, r);
      }
      dp.set(dp.x * r, dp.y);
    }

    {
      Rectangle rect = new Rectangle(getPosition().x + dp.x, getPosition().y + dp.y, getSize().x, getSize().y);
      double r = 1;
      for (int i : getScene().getGameObjects().keySet()) {
        GameObject gameObject = getScene().getGameObjects().get(i);
        if (!(gameObject instanceof Block))
          continue;
        Block block = (Block) gameObject;
        if (!Rectangle.intersect(rect, block.getRectangle()))
          continue;
        double tr = 1;
        if (dp.y > 0) {
          tr = (block.getPosition().y - getRectangle().y1) / dp.y;
          if (tr < 0) {
            tr = 1;
          } else {
            airborne = false;
          }
        } else if (dp.y == 0) {
          tr = 1;
        } else {
          tr = (getPosition().y - block.getRectangle().y1) / -dp.y;
          if (tr < 0)
            tr = 1;
        }
        tr = Math.min(tr, 1);
        r = Math.min(tr, r);
      }
      dp.set(dp.x, dp.y * r);
    }

    setPosition(getPosition().add(dp));
    velocity = dp;

    currentForce = new DVector2(0, mass * g);
  }

  protected final DVector2 getVelocity() {
    return velocity.clone();
  }

  protected final boolean isAirborne() {
    return airborne;
  }

  protected final void setVelocity(DVector2 velocity) {
    this.velocity = velocity;
  }

  protected final void setDrag(double drag) {
    this.drag = drag;
  }
}
