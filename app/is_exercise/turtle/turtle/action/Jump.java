package turtle.action;

import javafx.geometry.Point2D;
import turtle.Turtle;

public class Jump extends Action {
  private double length;

  public Jump(double length) {
    this.length = length;
  }

  public void act(Turtle.Controller tController) {
    Point2D from = new Point2D(tController.getX(), tController.getY());
    double rad = tController.getAngle() / 360 * Math.PI * 2;
    Point2D direction = new Point2D(Math.cos(rad), Math.sin(rad));
    Point2D to = from.add(direction.multiply(length));
    tController.setX(to.getX());
    tController.setY(to.getY());
  }
}
