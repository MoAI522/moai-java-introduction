package com.moai.cw.graphic_fx;

import java.util.ArrayList;

import com.moai.cw.App;
import com.moai.cw.util.GraphicObject;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FXMain extends Application {
  GraphicsContext gc;
  Drawer drawer;
  Runnable callback;

  @Override
  public void start(Stage st) throws Exception {
    Group root = new Group();
    Canvas canvas = new Canvas();
    gc = canvas.getGraphicsContext2D();
    root.getChildren().add(canvas);
    Scene scene = new Scene(root, 512, 448);
    st.setScene(scene);
    st.setTitle("運河に願いを - Canal Wishes");
    // st.setResizable(false);
    st.show();
    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, 100, 100);
    drawer = new Drawer();
    new App(this);
  }

  public static void init(String[] args) {
    launch(args);
  }

  public void draw(ArrayList<GraphicObject> objects, double fps) {
    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, 512, 448);
    if (drawer == null || gc == null)
      return;
    // System.out.println("fxmain-draw fps:" + fps);
    drawer.drawObjects(objects, gc);
    // gc.setFill(Color.WHITE);
    gc.fillText("FPS: " + fps, 5, 5);
  }
}
