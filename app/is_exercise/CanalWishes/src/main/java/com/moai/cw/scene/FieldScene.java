package com.moai.cw.scene;

import com.moai.cw.App;
import com.moai.cw.Constants;
import com.moai.cw.entity.Player;
import com.moai.cw.game_object.Camera;
import com.moai.cw.game_object.EventAreaManager;
import com.moai.cw.game_object.HitManager;
import com.moai.cw.game_object.Stage;
import com.moai.cw.store.FieldStore;
import com.moai.cw.ui.FieldUI;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.Rectangle;

public class FieldScene extends Scene {
  private static final int[] mainLayers = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, Constants.LAYER_DEBUG };
  private static final int[] uiLayers = { 10, 11, 12, 13, 14 };

  private Stage stage;
  private Camera mainCamera;
  private Player player;
  private FieldStore store;
  private EventAreaManager eventAreaManager;

  public FieldScene(App app) {
    super(app);

    store = new FieldStore();
    new FieldUI(this, store);
    new HitManager(this);
    eventAreaManager = new EventAreaManager(this);
    Camera uiCamera = new Camera(this, uiLayers, 1);
    addCamera(uiCamera);
    stage = new Stage(this);
    moveStage("0");
  }

  public FieldStore getStore() {
    return store;
  }

  public EventAreaManager getEventAreaManager() {
    return eventAreaManager;
  }

  public void moveStage(String mapID) {
    if (mainCamera != null)
      removeCamera(mainCamera);
    if (player != null)
      player.destroy();

    stage.loadStage(mapID);
    player = new Player(this, new DVector2(0, 0));
    mainCamera = new Camera(this, mainLayers, 0, player,
        new DVector2(Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT - Constants.STATUS_HEIGHT),
        new Rectangle(0, 0, stage.getMapSize().x, stage.getMapSize().y));
    addCamera(mainCamera);
  }
}
