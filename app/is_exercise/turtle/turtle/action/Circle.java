package turtle.action;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import turtle.Turtle.Controller;

public class Circle extends DrawableAction {
  private static final int APPROXIMATE_VERTICES = 8;

  private double radius;
  private boolean isFill;

  public Circle(double radius, boolean isFill) {
    this.radius = radius;
    this.isFill = isFill;
  }

  @Override
  public void draw(GraphicsContext gc, Controller tController, Point2D origin) {
    Point2D absCenter = new Point2D(tController.getX(), tController.getY()).add(origin);
    if (isFill) {
      gc.fillOval(absCenter.getX() - radius, absCenter.getY() - radius, radius * 2, radius * 2);
    } else {
      gc.strokeOval(absCenter.getX() - radius, absCenter.getY() - radius, radius * 2, radius * 2);
    }
  }

  @Override
  public Point2D[] getVertices(Controller tController) {
    Point2D[] ret = new Point2D[APPROXIMATE_VERTICES];
    for (int i = 0; i < APPROXIMATE_VERTICES; i++) {
      Point2D center = new Point2D(tController.getX(), tController.getY());
      double rad = 2 * Math.PI / APPROXIMATE_VERTICES * i;
      double size = (radius + (!isFill ? tController.getStrokeWidth() / 2 : 0))
          / Math.cos(Math.PI / APPROXIMATE_VERTICES);
      ret[i] = center.add(new Point2D(Math.cos(rad), Math.sin(rad)).multiply(size));
    }
    return ret;
  }

  @Override
  public void act(Controller tController) {
    return;
  }
}
