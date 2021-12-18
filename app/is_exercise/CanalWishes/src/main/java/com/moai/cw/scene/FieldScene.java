package com.moai.cw.scene;

import com.moai.cw.App;
import com.moai.cw.entity.Player;
import com.moai.cw.game_object.Camera;
import com.moai.cw.game_object.Stage;
import com.moai.cw.util.DPosition;

public class FieldScene extends Scene {
  public FieldScene(App app) {
    super(app);
    Camera camera = new Camera(this);
    addGameObject(camera);
    addCamera(camera);

    addGameObject(new Stage(this, "map/0.csv"));

    addGameObject(new Player(this, new DPosition(10, 10)));
  }
}
