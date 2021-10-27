package turtle.action;

import javafx.scene.canvas.GraphicsContext;
import turtle.Turtle;

public abstract class DrawableAction extends Action {
  public abstract void draw(GraphicsContext gc, Turtle.Controller tController, double originX, double originY);
}
