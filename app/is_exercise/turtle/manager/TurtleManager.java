package manager;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import turtle.Turtle;

public class TurtleManager {
  private ArrayList<Turtle> turtles;

  public TurtleManager() {
    this.turtles = new ArrayList<>();
  }

  public void add(Turtle t) {
    turtles.add(t);
  }

  public void remove(Turtle t) {
    turtles.remove(t);
  }

  public void draw(GraphicsContext gc) {
    turtles.forEach((t) -> {
      t.paint(gc);
    });
  }

  public class PickResult {
    public Turtle t;
    public Point2D offset;

    public PickResult(Turtle t, Point2D offset) {
      this.t = t;
      this.offset = offset;
    }
  }

  public PickResult pick(Point2D point) {
    for (Turtle t : turtles) {
      Point2D offset = t.find(point);
      if (offset != null) {
        return new PickResult(t, offset);
      }
    }
    return null;
  }
}
