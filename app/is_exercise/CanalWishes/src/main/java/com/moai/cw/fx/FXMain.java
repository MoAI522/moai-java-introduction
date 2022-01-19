package com.moai.cw.fx;

import java.util.ArrayList;
import java.util.HashMap;

import com.moai.cw.App;
import com.moai.cw.Constants;
import com.moai.cw.KeyInputManager;
import com.moai.cw.interfaces.FWController;
import com.moai.cw.util.GraphicObject;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FXMain extends Application implements FWController {
  GraphicsContext gc;
  Drawer drawer;
  SoundPlayer soundPlayer;
  Runnable callback;

  @Override
  public void start(Stage st) throws Exception {
    Group root = new Group();
    Canvas canvas = new Canvas(Constants.DISPLAY_WIDTH * Constants.PIXEL_RATIO,
        Constants.DISPLAY_HEIGHT * Constants.PIXEL_RATIO);
    root.getChildren().add(canvas);
    gc = canvas.getGraphicsContext2D();
    Scene scene = new Scene(root, Constants.DISPLAY_WIDTH * Constants.PIXEL_RATIO,
        Constants.DISPLAY_HEIGHT * Constants.PIXEL_RATIO);
    st.setScene(scene);
    st.setTitle("運河に願いを - Canal Wishes");
    // st.setResizable(false);
    st.show();
    drawer = new Drawer();
    soundPlayer = new SoundPlayer();
    App app = new App(this);

    st.setOnCloseRequest(event -> app.stop());
    scene.setOnKeyPressed(event -> onKeyPressed(event, app));
    scene.setOnKeyReleased(event -> onKeyReleased(event, app));
  }

  public static void init(String[] args) {
    launch(args);
  }

  @Override
  public void draw(ArrayList<GraphicObject> objects, HashMap<String, String> debugInfo) {
    if (drawer == null || gc == null)
      return;
    WritableImage buf = new WritableImage(Constants.DISPLAY_WIDTH * Constants.PIXEL_RATIO,
        Constants.DISPLAY_HEIGHT * Constants.PIXEL_RATIO);
    PixelWriter pw = buf.getPixelWriter();
    drawer.drawObjects(objects, pw);

    gc.setFill(Color.BLUE);
    gc.fillRect(0, 0, Constants.DISPLAY_WIDTH * Constants.PIXEL_RATIO,
        Constants.DISPLAY_HEIGHT * Constants.PIXEL_RATIO);
    gc.drawImage(buf, 0, 0);

    if (Constants.DEBUG) {
      String debugStr = "";
      for (String key : debugInfo.keySet()) {
        debugStr = debugStr + key + ": " + debugInfo.get(key) + "\n";
      }
      gc.setFill(new Color(0, 0, 0, 0.3));
      gc.fillRect(0, 0, 256, 20 * debugInfo.size());
      gc.setFill(Color.WHITE);
      gc.fillText(debugStr, 10, 20);
    }
  }

  private static void onKeyPressed(KeyEvent e, App app) {
    app.getKeyInputManager().onKeyPress(translateKeyCode(e.getCode()));
  }

  private static void onKeyReleased(KeyEvent e, App app) {
    app.getKeyInputManager().onKeyRelease(translateKeyCode(e.getCode()));
  }

  private static KeyInputManager.KeyCode translateKeyCode(KeyCode keyCode) {
    switch (keyCode) {
      case W:
        return KeyInputManager.KeyCode.W;
      case A:
        return KeyInputManager.KeyCode.A;
      case S:
        return KeyInputManager.KeyCode.S;
      case D:
        return KeyInputManager.KeyCode.D;
      case B:
        return KeyInputManager.KeyCode.B;
      case N:
        return KeyInputManager.KeyCode.N;
      case M:
        return KeyInputManager.KeyCode.M;
      case SPACE:
        return KeyInputManager.KeyCode.SPACE;
      case ESCAPE:
        return KeyInputManager.KeyCode.ESC;
      default:
        return null;
    }
  }

  @Override
  public void playSound(int index) {
    soundPlayer.play(index);
  }

  @Override
  public void stopSound(int index) {
    soundPlayer.stop(index);
  }
}
