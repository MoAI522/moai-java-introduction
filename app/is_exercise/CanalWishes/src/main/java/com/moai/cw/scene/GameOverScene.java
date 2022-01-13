package com.moai.cw.scene;

import com.moai.cw.App;
import com.moai.cw.Constants;
import com.moai.cw.game_object.Camera;
import com.moai.cw.gameover.GameOver;
import com.moai.cw.gameover.GameOverBG;
import com.moai.cw.gameover.GameOverText;
import com.moai.cw.util.DVector2;

public class GameOverScene extends Scene {

  public GameOverScene(App app) {
    super(app);

    int[] layers = { 0, 1 };
    addCamera(new Camera(this, layers, 0));
    new GameOver(this);
    new GameOverBG(this);
    new GameOverText(this, new DVector2(Constants.DISPLAY_WIDTH / 2 - 49, Constants.DISPLAY_HEIGHT / 2 - 10));
  }

}
