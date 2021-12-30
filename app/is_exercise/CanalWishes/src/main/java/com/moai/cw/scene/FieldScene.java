package com.moai.cw.scene;

import com.moai.cw.App;
import com.moai.cw.Constants;
import com.moai.cw.entity.Player;
import com.moai.cw.game_object.Camera;
import com.moai.cw.game_object.Stage;
import com.moai.cw.ui.UI;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Rectangle;

public class FieldScene extends Scene {
  public static final int[] mainLayers = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
  public static final int[] uiLayers = { 10, 11, 12, 13, 14 };

  public FieldScene(App app) {
    super(app);

    Stage stage = new Stage(this, "map/0.csv");
    Player player = new Player(this, new DVector2(100, 10));
    new UI(this);
    Camera mainCamera = new Camera(this, mainLayers, player,
        new DVector2(Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT - Constants.STATUS_HEIGHT),
        new Rectangle(0, 0, stage.getMapSize().x, stage.getMapSize().y));
    addCamera(mainCamera);
    Camera uiCamera = new Camera(this, uiLayers);
    addCamera(uiCamera);
  }
}
