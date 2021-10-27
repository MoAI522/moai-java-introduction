package turtle.action;

import turtle.Turtle;

public class ChangePenSize extends Action {
  private double strokeWidth;

  public ChangePenSize(double strokeWidth) {
    this.strokeWidth = strokeWidth;
  }

  @Override
  public void act(Turtle.Controller tController) {
    tController.setStrokeWidth(strokeWidth);
  }
}
