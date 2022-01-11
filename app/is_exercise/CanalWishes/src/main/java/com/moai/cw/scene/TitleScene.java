package com.moai.cw.scene;

import com.moai.cw.App;
import com.moai.cw.game_object.Camera;
import com.moai.cw.title.Title;
import com.moai.cw.title.TitleBG;
import com.moai.cw.title.TitleLogo;
import com.moai.cw.title.TitleText;
import com.moai.cw.util.DVector2;

public class TitleScene extends Scene {

  public TitleScene(App app) {
    super(app);
    int[] layers = { 0, 1 };
    addCamera(new Camera(this, layers, 0));
    new Title(this, null);
    new TitleBG(this, new DVector2(0, 0));
    new TitleLogo(this, new DVector2(22, 51));
    new TitleText(this, new DVector2(56, 164));
  }

}
