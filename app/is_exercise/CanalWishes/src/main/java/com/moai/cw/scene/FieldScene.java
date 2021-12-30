package com.moai.cw.scene;

import com.moai.cw.App;
import com.moai.cw.entity.Player;
import com.moai.cw.game_object.Camera;
import com.moai.cw.game_object.Stage;
import com.moai.cw.util.DVector2;

public class FieldScene extends Scene {
  public FieldScene(App app) {
    super(app);
    Camera camera = new Camera(this);
    addCamera(camera);

    new Stage(this, "map/0.csv");
    new Player(this, new DVector2(100, 10));
  }
}
