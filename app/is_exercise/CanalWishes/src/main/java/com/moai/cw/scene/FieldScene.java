package com.moai.cw.scene;

import com.moai.cw.App;
import com.moai.cw.game_object.Camera;
import com.moai.cw.game_object.Stage;

public class FieldScene extends Scene {
  public FieldScene(App app) {
    super(app);
    Camera camera = new Camera(this);
    addGameObject(camera);
    addCamera(camera);

    String filePath = Thread.currentThread().getContextClassLoader().getResource("map/0.csv").getPath();
    addGameObject(new Stage(this, filePath));
  }

  @Override
  public void update(double dt) {

  }
}
