package turtle.action;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import turtle.Turtle;

public class Move extends DrawableAction {
  private double length;

  public Move(double length) {
    this.length = length;
  }

  @Override
  public void act(Turtle.Controller tController) {
    double rad = Math.PI * 2 * (tController.getAngle() / 360);
    tController.setX(tController.getX() + length * Math.cos(rad));
    tController.setY(tController.getY() + length * Math.sin(rad));
  }

  @Override
  public void draw(GraphicsContext gc, Turtle.Controller tController, Point2D origin) {
    double fromX = tController.getX() + origin.getX();
    double fromY = tController.getY() + origin.getY();
    double rad = Math.PI * 2 * (tController.getAngle() / 360);
    double toX = fromX + length * Math.cos(rad);
    double toY = fromY + length * Math.sin(rad);
    gc.strokeLine(fromX, fromY, toX, toY);
  }

  @Override
  public Point2D[] getVertices(Turtle.Controller tController) {
    Point2D from = new Point2D(tController.getX(), tController.getY());
    double rad = Math.PI * 2 * (tController.getAngle() / 360);
    Point2D to = from.add(new Point2D(length * Math.cos(rad), length * Math.sin(rad)));
    Point2D direction = new Point2D(to.getX(), to.getY()).subtract(from).normalize();
    Point2D widthVector = new Point2D(-direction.getY(), direction.getX()).multiply(tController.getStrokeWidth());
    Point2D[] ret = new Point2D[4];
    ret[0] = from.subtract(direction.multiply(tController.getStrokeWidth() / 2)).add(widthVector.multiply(-0.5));
    ret[1] = to.add(direction.multiply(tController.getStrokeWidth() / 2)).add(widthVector.multiply(-0.5));
    ret[2] = to.add(direction.multiply(tController.getStrokeWidth() / 2)).add(widthVector.multiply(0.5));
    ret[3] = from.subtract(direction.multiply(tController.getStrokeWidth() / 2)).add(widthVector.multiply(0.5));
    return ret;
  }
}
