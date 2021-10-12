package ui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class WindowManager {
  private Stage st;
  private Canvas canvas;
  private GraphicsContext gc;

  public WindowManager(Stage st) {
    this.st = st;
    this.canvas = new Canvas(640, 480);
    this.gc = this.canvas.getGraphicsContext2D();
  }

  public void initWindow() {
    Group root = new Group();
    root.getChildren().add(canvas);
    Scene scene = new Scene(root, 640, 480, Color.WHITE);
    st.setTitle("Graph Drawer");
    st.setScene(scene);
    st.show();
  }

  public void setTitle(String title) {
    st.setTitle(title);
  }

  public GraphicsContext getGc() {
    return gc;
  }
}
