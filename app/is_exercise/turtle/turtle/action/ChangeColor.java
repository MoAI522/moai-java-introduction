package turtle.action;

import javafx.scene.paint.Color;
import turtle.Turtle;

public class ChangeColor extends Action {
  private Color color;

  public ChangeColor(Color color) {
    this.color = color;
  }

  @Override
  public void act(Turtle.Controller tController) {
    tController.setColor(color);
  }
}
