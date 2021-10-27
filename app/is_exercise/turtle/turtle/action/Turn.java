package turtle.action;

import turtle.Turtle;

public class Turn extends Action {
  private double amount;

  public Turn(double amount) {
    this.amount = amount;
  }

  @Override
  public void act(Turtle.Controller tController) {
    tController.setAngle((tController.getAngle() + amount) % 360);
  }
}
