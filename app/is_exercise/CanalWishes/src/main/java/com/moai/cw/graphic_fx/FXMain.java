package com.moai.cw.graphic_fx;

import java.util.ArrayList;

import com.moai.cw.App;
import com.moai.cw.Constants;
import com.moai.cw.util.GraphicObject;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FXMain extends Application {
  GraphicsContext gc;
  Drawer drawer;
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
    App app = new App(this);

    st.setOnCloseRequest(event -> app.stop());
  }

  public static void init(String[] args) {
    launch(args);
  }

  public void draw(ArrayList<GraphicObject> objects, double fps) {
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
    gc.setFill(Color.WHITE);
    gc.fillText("FPS: " + fps, 5, 20);
  }
}
