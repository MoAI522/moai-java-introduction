package turtle.action;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import turtle.Turtle;

public abstract class DrawableAction extends Action {
  public abstract void draw(GraphicsContext gc, Turtle.Controller tController, Point2D origin);

  public abstract Point2D[] getVertices(Turtle.Controller tController);
}
