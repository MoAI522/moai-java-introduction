package ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WindowManager {
  private Stage st;
  private Canvas canvas;
  private GraphicsContext gc;

  public WindowManager(Stage st) {
    this.st = st;
    Rectangle2D screenSize = Screen.getPrimary().getBounds();
    canvas = new Canvas(screenSize.getWidth(), screenSize.getHeight());
    gc = canvas.getGraphicsContext2D();
  }

  public void initWindow(int w, int h) {
    BorderPane root = new BorderPane();
    root.getChildren().add(canvas);

    GVMenuBar menu = new GVMenuBar();
    root.setTop(menu);

    canvas.resize(w, h);

    Scene scene = new Scene(root, w, h, Color.WHITE);
    st.setTitle("Graph Viewer");
    st.setScene(scene);
    st.show();
  }

  public void resetCanvas(int w, int h) {
    canvas.resize(w, h);
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  public void setTitle(String title) {
    st.setTitle(title);
  }

  public GraphicsContext getGc() {
    return gc;
  }
}
