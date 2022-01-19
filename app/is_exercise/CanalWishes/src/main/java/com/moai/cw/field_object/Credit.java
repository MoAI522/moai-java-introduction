package com.moai.cw.field_object;

import java.util.List;

import com.moai.cw.Constants;
import com.moai.cw.entity.Entity;
import com.moai.cw.entity.Player;
import com.moai.cw.game_object.GameObject;
import com.moai.cw.interfaces.Hittable;
import com.moai.cw.scene.FieldScene;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.CVector2;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.IVector2;

public class Credit extends Entity implements Hittable {
  private int index;

  public Credit(Scene scene, GameObject parent, IVector2 position, List<String> params) {
    super(scene, parent, new DVector2(position.x * Constants.MAPCHIP_SIZE, position.y * Constants.MAPCHIP_SIZE),
        new DVector2(1, 1), new CVector2(24, 216), new CVector2(36, 36), 0);
    index = Integer.valueOf(params.get(0));
    if (((FieldScene) getScene()).getStore().hasCredit(index)) {
      destroy();
    }
  }

  @Override
  public int getLayer() {
    return Constants.LAYER_FIELDOBJECT;
  }

  @Override
  public void onHit(GameObject target) {
    if (target instanceof Player) {
      ((FieldScene) getScene()).getStore().achieveCredit(index);
      getScene().getApp().getFWController().stopSound(0);
      getScene().getApp().getFWController().stopSound(1);
      getScene().getApp().getFWController().playSound(8);
      destroy();
    }
  }

  @Override
  public void update(int dt) {

  }

}
