package turtle;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import turtle.action.Action;
import turtle.action.ChangeColor;
import turtle.action.ChangePenSize;
import turtle.action.DrawableAction;
import turtle.action.Move;
import turtle.action.Turn;

public class Turtle {
  private final static double defaultX = 0.0;
  private final static double defaultY = 0.0;
  private final static double defaultAngle = 0.0;
  private final static double defaultStrokeWidth = 1.0;
  private final static Color defaultColor = Color.GREEN;

  private double currentX, currentY;
  private double originX, originY;
  private double currentAngle;
  private double strokeWidth;
  private Color color;
  private ArrayList<Action> actions;
  private Controller controller;

  public Turtle(double x, double y) {
    this.currentX = 0;
    this.currentY = 0;
    this.originX = x;
    this.originY = y;
    this.currentAngle = defaultAngle;
    this.strokeWidth = defaultStrokeWidth;
    this.color = defaultColor;
    this.actions = new ArrayList<>();
    this.controller = new Controller();

    actions.add(new Init());
  }

  public void move(double length) {
    actions.add(new Move(length));
  }

  public void turn(double deg) {
    actions.add(new Turn(deg));
  }

  public void penSize(double size) {
    actions.add(new ChangePenSize(size));
  }

  public void penColor(Color c) {
    actions.add(new ChangeColor(c));
  }

  public void paint(GraphicsContext gc) {
    actions.stream().forEach((action) -> {
      if (action instanceof DrawableAction) {
        gc.setStroke(color);
        gc.setLineWidth(strokeWidth);
        ((DrawableAction) action).draw(gc, controller, originX, originY);
      }
      action.act(controller);
      System.out.println(currentX + " " + currentY + " " + currentAngle + " ");
    });
  }

  public void translate(Point2D point) {
    this.originX = point.getX();
    this.originY = point.getY();
  }

  public class Controller {
    public double getX() {
      return Turtle.this.currentX;
    }

    public double getY() {
      return Turtle.this.currentY;
    }

    public double getAngle() {
      return Turtle.this.currentAngle;
    }

    public double getStrokeWidth() {
      return Turtle.this.strokeWidth;
    }

    public Color getColor() {
      return Turtle.this.color;
    }

    public void setX(double x) {
      Turtle.this.currentX = x;
    }

    public void setY(double y) {
      Turtle.this.currentY = y;
    }

    public void setAngle(double angle) {
      Turtle.this.currentAngle = angle;
    }

    public void setStrokeWidth(double strokeWidth) {
      Turtle.this.strokeWidth = strokeWidth;
    }

    public void setColor(Color color) {
      Turtle.this.color = color;
    }
  }

  private class Init extends Action {
    public void act(Turtle.Controller tController) {
      tController.setX(Turtle.defaultX);
      tController.setY(Turtle.defaultY);
      tController.setAngle(Turtle.defaultAngle);
      tController.setStrokeWidth(Turtle.defaultStrokeWidth);
      tController.setColor(Turtle.defaultColor);
    }
  }
}
