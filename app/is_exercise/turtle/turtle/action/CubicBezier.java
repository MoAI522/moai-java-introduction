package turtle.action;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import turtle.Turtle;

public class CubicBezier extends DrawableAction {
  private double length;
  private Point2D c1, c2;

  public CubicBezier(double length, Point2D c1, Point2D c2) {
    this.length = length;
    this.c1 = c1;
    this.c2 = c2;
  }

  @Override
  public void act(Turtle.Controller tController) {
    Point2D from = new Point2D(tController.getX(), tController.getY());
    double rad = tController.getAngle() / 360 * Math.PI * 2;
    Point2D to = from.add(new Point2D(Math.cos(rad), Math.sin(rad)).multiply(length));
    tController.setX(to.getX());
    tController.setY(to.getY());
  }

  @Override
  public void draw(GraphicsContext gc, Turtle.Controller tController, Point2D origin) {
    Point2D from = new Point2D(tController.getX(), tController.getY()).add(origin);
    double rad = tController.getAngle() / 360 * Math.PI * 2;
    Point2D direction = new Point2D(Math.cos(rad), Math.sin(rad));
    Point2D to = from.add(direction.multiply(length));
    Point2D absC1 = from.add(new Point2D(c1.getX() * Math.cos(rad) + c1.getY() * -Math.sin(rad),
        c1.getX() * Math.sin(rad) + c1.getY() * Math.cos(rad)));
    Point2D absC2 = to.add(new Point2D(c2.getX() * Math.cos(rad) + c2.getY() * -Math.sin(rad),
        c2.getX() * Math.sin(rad) + c2.getY() * Math.cos(rad)));
    gc.beginPath();
    gc.moveTo(from.getX(), from.getY());
    gc.bezierCurveTo(absC1.getX(), absC1.getY(), absC2.getX(), absC2.getY(), to.getX(), to.getY());
    gc.stroke();
  }

  @Override
  public Point2D[] getVertices(Turtle.Controller tController) {
    Point2D from = new Point2D(tController.getX(), tController.getY());
    double rad = tController.getAngle() / 360 * Math.PI * 2;
    Point2D direction = new Point2D(Math.cos(rad), Math.sin(rad));
    Point2D to = from.add(direction.multiply(length));
    Point2D absC1 = from.add(new Point2D(c1.getX() * Math.cos(rad) + c1.getY() * -Math.sin(rad),
        c1.getX() * Math.sin(rad) + c1.getY() * Math.cos(rad)));
    Point2D absC2 = to.add(new Point2D(c2.getX() * Math.cos(rad) + c2.getY() * -Math.sin(rad),
        c2.getX() * Math.sin(rad) + c2.getY() * Math.cos(rad)));
    Point2D[] ret = { from, to, absC1, absC2 };
    return ret;
  }
}
