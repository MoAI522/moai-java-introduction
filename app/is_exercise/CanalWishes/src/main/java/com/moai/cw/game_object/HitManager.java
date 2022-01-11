package com.moai.cw.game_object;

import java.util.ArrayList;

import com.moai.cw.interfaces.Hittable;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Rectangle;

public class HitManager extends GameObject {

  public HitManager(Scene scene) {
    super(scene, new DVector2(0, 0));
  }

  @Override
  public void update(int dt) {
    ArrayList<HitTupple> hits = new ArrayList<HitTupple>();
    for (int key : getScene().getGameObjects().keySet()) {
      GameObject object = getScene().getGameObjects().get(key);
      if (!(object instanceof Hittable))
        continue;
      for (int _key : getScene().getGameObjects().keySet()) {
        if (_key == key)
          continue;
        GameObject _object = getScene().getGameObjects().get(_key);
        if (!(_object instanceof Hittable))
          continue;
        if (Rectangle.intersect(((Hittable) object).getRectangle(), ((Hittable) _object).getRectangle())) {
          hits.add(new HitTupple((Hittable) object, _object));
        }
      }
    }
    for (HitTupple hit : hits) {
      hit.hittable.onHit(hit.target);
    }
  }

  private static class HitTupple {
    public Hittable hittable;
    public GameObject target;

    public HitTupple(Hittable hittable, GameObject target) {
      this.hittable = hittable;
      this.target = target;
    }
  }
}
