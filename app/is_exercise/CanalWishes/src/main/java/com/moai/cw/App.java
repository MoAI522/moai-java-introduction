package com.moai.cw;

import com.moai.cw.graphic_fx.FXMain;
import com.moai.cw.scene.FieldScene;
import com.moai.cw.scene.Scene;

public class App extends Thread {
  private FXMain graphics;

  private Scene currentScene;
  private KeyInputManager keyInputManager;

  public App(FXMain graphics) {
    this.graphics = graphics;
    keyInputManager = new KeyInputManager();
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
      int dt = (int) (time - previousTime);
      previousTime = time;
      try {
        int sleepTime = (int) Math.max((double) 1000 / targetFPS * fpsCount - (time - prevCalcFPSTime), 0);
        sleep(sleepTime);
      } catch (InterruptedException e) {
      }
      fpsCount++;
      if (fpsCount == 16) {
        long sum = prevCalcFPSTime - time;
        int ave = (int) (sum / 16);
        fps = 1000 / ave;
        fpsCount = 0;
        prevCalcFPSTime = time;
      }

      keyInputManager.update();
      currentScene.update(dt);

      graphics.draw(currentScene.draw(), fps);
    }
  }

  public KeyInputManager getKeyInputManager() {
    return keyInputManager;
  }

  public static void main(String[] args) {
    FXMain.init(args);
  }
}
