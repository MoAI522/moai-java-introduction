package com.moai.cw;

import com.moai.cw.graphic_fx.FXMain;
import com.moai.cw.scene.FieldScene;
import com.moai.cw.scene.Scene;

public class App extends Thread {
  private FXMain graphics;

  private Scene currentScene;

  public App(FXMain graphics) {
    this.graphics = graphics;
    this.start();
  }

  public void run() {
    currentScene = new FieldScene(this);
    update(System.currentTimeMillis());
  }

  public void update(long previousTime) {
    long time = System.currentTimeMillis();
    double dt = time - previousTime;
    double fps = 1000 / dt;
    // System.out.println("update fps:" + fps);

    currentScene.update(dt);

    graphics.draw(currentScene.draw(), fps);

    try {
      sleep(10);
    } catch (InterruptedException e) {
    }

    update(time);
  }

  public static void main(String[] args) {
    FXMain.init(args);
  }
}
