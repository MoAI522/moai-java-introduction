package turtle;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import turtle.action.Action;
import turtle.action.ChangeColor;
import turtle.action.ChangePenSize;
import turtle.action.Circle;
import turtle.action.CubicBezier;
import turtle.action.DrawableAction;
import turtle.action.Jump;
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

  private Point2D[] hitbox;
  private Point2D[] currentVertices;

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

  public void cbezier(double length, Point2D c1, Point2D c2) {
    actions.add(new CubicBezier(length, c1, c2));
  }

  public void jump(double length) {
    actions.add(new Jump(length));
  }

  public void circle(double radius, boolean isFill) {
    actions.add(new Circle(radius, isFill));
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
        gc.setFill(color);
        gc.setLineWidth(strokeWidth);
        ((DrawableAction) action).draw(gc, controller, new Point2D(originX, originY));
      }
      action.act(controller);
    });

    gc.setFill(Color.rgb(255, 0, 0, 0.5));
    if (hitbox == null)
      return;
    gc.fillPolygon(Arrays.stream(hitbox).mapToDouble((p) -> {
      return p.getX() + originX;
    }).toArray(), Arrays.stream(hitbox).mapToDouble((p) -> {
      return p.getY() + originY;
    }).toArray(), hitbox.length);
    gc.setFill(Color.rgb(255, 0, 0));
    for (Point2D v : currentVertices) {
      gc.fillOval(v.getX() - 2 + originX, v.getY() - 2 + originY, 4, 4);
    }
  }

  public void translate(Point2D point) {
    this.originX = point.getX();
    this.originY = point.getY();
  }

  public Point2D find(Point2D target) {
    ArrayList<Point2D> allVertices = new ArrayList<>();
    actions.stream().forEach((action) -> {
      if (action instanceof DrawableAction) {
        Point2D[] vertices = ((DrawableAction) action).getVertices(controller);
        for (Point2D vertex : vertices) {
          allVertices.add(vertex);
        }
      }
      action.act(controller);
    });
    currentVertices = allVertices.toArray(new Point2D[allVertices.size()]);

    ArrayList<Point2D> polygon = new ArrayList<>();

    Point2D currentVertex = allVertices.get(0);
    Point2D currentDirection = new Point2D(1, 0);
    int currentIndex = 0, startIndex = 0;

    for (int i = 1; i < allVertices.size(); i++) {
      Point2D vertex = allVertices.get(i);
      if (vertex.getY() < currentVertex.getY()) {
        currentVertex = vertex;
        startIndex = i;
        currentIndex = i;
      }
    }

    polygon.add(currentVertex);

    while (true) {
      Point2D nextVertex = currentVertex;
      Point2D nextDirection = currentDirection;
      int nextIndex = currentIndex;
      double minRad = Math.PI * 2;

      for (int i = 0; i < allVertices.size(); i++) {
        if (i == currentIndex)
          continue;
        Point2D vertex = allVertices.get(i);
        Point2D direction = vertex.subtract(currentVertex).normalize();
        double cosVal = currentDirection.dotProduct(direction);
        double sinVal = currentDirection.crossProduct(direction).magnitude();
        double rad;
        if (sinVal >= 0) {
          rad = Math.acos(cosVal);
        } else {
          rad = Math.PI * 2 + -Math.acos(cosVal);
        }
        if (rad < minRad && vertex.subtract(currentVertex).magnitude() > 0) {
          nextVertex = vertex;
          nextDirection = direction;
          nextIndex = i;
          minRad = rad;
        }
      }

      if (nextIndex == startIndex || nextIndex == currentIndex)
        break;
      polygon.add(nextVertex);
      currentVertex = nextVertex;
      currentDirection = nextDirection;
      currentIndex = nextIndex;
    }

    hitbox = polygon.toArray(new Point2D[polygon.size()]);

    Point2D targetRelativePos = target.subtract(new Point2D(originX, originY));
    for (int i = 0; i < polygon.size(); i++) {
      Point2D edgeDirection = polygon.get((i + 1) % polygon.size()).subtract(polygon.get(i)).normalize();
      Point2D targetDirection = targetRelativePos.subtract(polygon.get(i)).normalize();
      double sinVal = edgeDirection.crossProduct(targetDirection).getZ();
      if (sinVal < 0) {
        return null;
      }
    }

    return targetRelativePos;
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
