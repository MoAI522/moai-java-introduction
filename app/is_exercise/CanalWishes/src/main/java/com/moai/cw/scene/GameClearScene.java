package com.moai.cw.scene;

import com.moai.cw.App;
import com.moai.cw.Constants;
import com.moai.cw.game_object.Camera;
import com.moai.cw.gameclear.GameClear;
import com.moai.cw.gameclear.GameClearBG;
import com.moai.cw.gameclear.GameClearText;
import com.moai.cw.util.DVector2;

public class GameClearScene extends Scene {

  public GameClearScene(App app) {
    super(app);

    int[] layers = { 0, 1 };
    addCamera(new Camera(this, layers, 0));
    new GameClear(this);
    new GameClearBG(this);
    new GameClearText(this, new DVector2(Constants.DISPLAY_WIDTH / 2 - 113, Constants.DISPLAY_HEIGHT / 2 - 29));
  }

}
