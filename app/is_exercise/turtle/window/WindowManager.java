package window;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class WindowManager {
  private final static int defaultWidth = 640;
  private final static int defaultHeight = 480;

  private GraphicsContext gc;
  private IOnResizeFunc onResize;

  public WindowManager(Stage st) {
    Group root = new Group();
    Canvas canvas = new Canvas(defaultWidth, defaultHeight);
    root.getChildren().add(canvas);

    gc = canvas.getGraphicsContext2D();

    Scene scene = new Scene(root, defaultWidth, defaultHeight);
    st.setScene(scene);
    st.setTitle("Turtle Graphics");

    st.widthProperty().addListener((obs, oldValue, newValue) -> {
      if (onResize == null)
        return;
      onResize.run(newValue.intValue(), st.heightProperty().intValue());
    });
    st.heightProperty().addListener((obs, oldValue, newValue) -> {
      if (onResize == null)
        return;
      onResize.run(st.widthProperty().intValue(), newValue.intValue());
    });

    st.show();
  }

  public void setOnResize(IOnResizeFunc onResize) {
    this.onResize = onResize;
  }

  public GraphicsContext getGC() {
    return gc;
  }
}
