package com.moai.cw.scene;

import com.moai.cw.App;
import com.moai.cw.Constants;
import com.moai.cw.entity.Player;
import com.moai.cw.game_object.BackGround;
import com.moai.cw.game_object.Camera;
import com.moai.cw.game_object.EventAreaManager;
import com.moai.cw.game_object.GameStateManager;
import com.moai.cw.game_object.HitManager;
import com.moai.cw.game_object.Stage;
import com.moai.cw.store.FieldStore;
import com.moai.cw.ui.FieldUI;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.IVector2;
import com.moai.cw.util.Rectangle;

public class FieldScene extends Scene {
  private static final int[] bgLayers = { Constants.LAYER_BACKGROUND };
  private static final int[] mainLayers = { Constants.LAYER_BLOCKS, Constants.LAYER_FIELDOBJECT, Constants.LAYER_ENEMY,
      Constants.LAYER_PLAYER, Constants.LAYER_FIREBALL, Constants.LAYER_DEBUG };
  private static final int[] uiLayers = { 10, 11, 12, 13, 14 };

  private Stage stage;
  private Camera mainCamera;
  private Player player;
  private FieldStore store;
  private EventAreaManager eventAreaManager;
  private BackGround backGround;

  public FieldScene(App app) {
    super(app);

    store = new FieldStore();
    new GameStateManager(this);
    new FieldUI(this, store);
    new HitManager(this);
    eventAreaManager = new EventAreaManager(this);
    backGround = new BackGround(this);
    Camera bgCamera = new Camera(this, bgLayers, 0);
    addCamera(bgCamera);
    Camera uiCamera = new Camera(this, uiLayers, 2);
    addCamera(uiCamera);
    stage = new Stage(this);
    moveStage(0, new IVector2(2, 3));

    getApp().getFWController().playSound(0);
  }

  public FieldStore getStore() {
    return store;
  }

  public EventAreaManager getEventAreaManager() {
    return eventAreaManager;
  }

  public void moveStage(int mapID, IVector2 destination) {
    if (mainCamera != null)
      removeCamera(mainCamera);
    if (player != null)
      player.destroy();

    stage.loadStage(mapID);
    player = new Player(this,
        new DVector2(destination.x * Constants.MAPCHIP_SIZE, destination.y * Constants.MAPCHIP_SIZE));
    mainCamera = new Camera(this, mainLayers, 1, player,
        new DVector2(Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT - Constants.STATUS_HEIGHT),
        new Rectangle(0, 0, stage.getMapSize().x, stage.getMapSize().y));
    addCamera(mainCamera);
    backGround.setIndex(mapID);
  }
}
