package drag;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import manager.TurtleManager;
import manager.TurtleManager.PickResult;
import turtle.Turtle;
import window.WindowManager;

public class DragManager {
  private TurtleManager tm;
  private WindowManager wm;
  private Turtle activeTurtle = null;
  private Point2D currentOffset;

  public DragManager(TurtleManager tm, WindowManager wm) {
    this.tm = tm;
    this.wm = wm;

    Canvas canvas = wm.getCanvas();
    canvas.setOnMousePressed((MouseEvent e) -> onDragEntered(new Point2D(e.getSceneX(), e.getSceneY())));
    canvas.setOnMouseDragged((MouseEvent e) -> onDragged(new Point2D(e.getSceneX(), e.getSceneY())));
    canvas.setOnMouseReleased((MouseEvent e) -> onDragExited());
  }

  private void onDragEntered(Point2D point) {
    PickResult result = tm.pick(point);
    if (result == null)
      return;
    activeTurtle = result.t;
    currentOffset = result.offset;
  }

  private void onDragged(Point2D point) {
    if (activeTurtle == null)
      return;
    activeTurtle.translate(point.subtract(currentOffset));
    wm.update();
  }

  private void onDragExited() {
    activeTurtle = null;
  }
}
