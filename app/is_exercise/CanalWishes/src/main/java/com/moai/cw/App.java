package com.moai.cw;

import java.util.HashMap;

import com.moai.cw.fx.FXMain;
import com.moai.cw.interfaces.FWController;
import com.moai.cw.scene.Scene;
import com.moai.cw.scene.TitleScene;

public class App extends Thread {
  private FWController fwController;

  private Scene currentScene;
  private KeyInputManager keyInputManager;
  private HashMap<String, String> debugInfo;

  public App(FWController fwController) {
    this.fwController = fwController;
    keyInputManager = new KeyInputManager();
    debugInfo = new HashMap<String, String>();
    this.start();
  }

  public void run() {
    currentScene = new TitleScene(this);

    long previousTime = System.currentTimeMillis() - 10;
    long prevCalcFPSTime = previousTime;
    int targetFPS = Constants.TARGET_FPS;
    int fpsCount = 0;
    double fps = 0;
    int dt = (int) Math.floor(1000 / targetFPS);
    while (true) {
      long time = System.currentTimeMillis();
      previousTime = time;
      try {
        int sleepTime = (int) Math.max((double) 1000 / targetFPS * fpsCount - (time - prevCalcFPSTime), 0);
        sleep(sleepTime);
      } catch (InterruptedException e) {
      }
      fpsCount++;
      if (fpsCount == 16) {
        long sum = time - prevCalcFPSTime;
        int ave = (int) (sum / 16);
        fps = 1000 / ave;
        fpsCount = 0;
        prevCalcFPSTime = time;
        setDebugInfo("fps", Double.toString(fps));
      }

      keyInputManager.update();
      currentScene.update(dt);

      fwController.draw(currentScene.draw(), debugInfo);
    }
  }

  public void setScene(Scene scene) {
    currentScene = scene;
  }

  public KeyInputManager getKeyInputManager() {
    return keyInputManager;
  }

  public FWController getFWController() {
    return fwController;
  }

  public void setDebugInfo(String key, String value) {
    debugInfo.put(key, value);
  }

  public static void main(String[] args) {
    FXMain.init(args);
  }
}
