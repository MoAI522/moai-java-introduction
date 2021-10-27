package turtle.action;

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
  public void draw(GraphicsContext gc, Turtle.Controller tController, double originX, double originY) {
    double fromX = tController.getX() + originX;
    double fromY = tController.getY() + originY;
    double rad = Math.PI * 2 * (tController.getAngle() / 360);
    double toX = fromX + length * Math.cos(rad);
    double toY = fromY + length * Math.sin(rad);
    gc.strokeLine(fromX, fromY, toX, toY);
  }
}
