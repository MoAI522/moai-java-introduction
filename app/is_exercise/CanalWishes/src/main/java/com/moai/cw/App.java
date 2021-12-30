package com.moai.cw;

import java.util.HashMap;

import com.moai.cw.graphic_fx.FXMain;
import com.moai.cw.scene.FieldScene;
import com.moai.cw.scene.Scene;

public class App extends Thread {
  private FXMain graphics;

  private Scene currentScene;
  private KeyInputManager keyInputManager;
  private HashMap<String, String> debugInfo;

  public App(FXMain graphics) {
    this.graphics = graphics;
    keyInputManager = new KeyInputManager();
    debugInfo = new HashMap<String, String>();
    this.start();
  }

  public void run() {
    currentScene = new FieldScene(this);

    long previousTime = System.currentTimeMillis() - 10;
    long prevCalcFPSTime = previousTime;
    int targetFPS = Constants.TARGET_FPS;
    int fpsCount = 0;
    double fps = 0;
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
      currentScene.update((int) Math.floor(1000 / targetFPS));

      graphics.draw(currentScene.draw(), debugInfo);
    }
  }

  public KeyInputManager getKeyInputManager() {
    return keyInputManager;
  }

  public void setDebugInfo(String key, String value) {
    debugInfo.put(key, value);
  }

  public static void main(String[] args) {
    FXMain.init(args);
  }
}
