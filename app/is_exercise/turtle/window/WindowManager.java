package window;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class WindowManager {
  private final static int defaultWidth = 640;
  private final static int defaultHeight = 480;

  private Canvas canvas;
  private GraphicsContext gc;
  private Runnable onRender;

  public WindowManager(Stage st) {
    Group root = new Group();
    canvas = new Canvas();
    canvas.widthProperty().bind(st.widthProperty());
    canvas.heightProperty().bind(st.heightProperty());
    root.getChildren().add(canvas);

    gc = canvas.getGraphicsContext2D();

    Scene scene = new Scene(root, defaultWidth, defaultHeight);
    st.setScene(scene);
    st.setTitle("Turtle Graphics");

    st.widthProperty().addListener((obs, oldValue, newValue) -> update());
    st.heightProperty().addListener((obs, oldValue, newValue) -> update());

    st.show();
  }

  public void update() {
    gc.clearRect(0, 0, canvas.widthProperty().doubleValue(), canvas.heightProperty().doubleValue());
    if (onRender == null)
      return;
    onRender.run();
  }

  public void setOnRender(Runnable onResize) {
    this.onRender = onResize;
  }

  public GraphicsContext getGC() {
    return gc;
  }

  public Canvas getCanvas() {
    return canvas;
  }
}
