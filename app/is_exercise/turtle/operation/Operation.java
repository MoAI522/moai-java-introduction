package operation;

import java.util.Random;

import turtle.Turtle;

public class Operation {
  public static void square(Turtle t) {
    t.penSize(1);
    for (int i = 0; i < 4; i++) {
      t.move(50);
      t.turn(90);
    }
  }

  public static void regularPolygon(Turtle t, int vertices, double size) {
    for (int i = 0; i < vertices; i++) {
      t.move(size);
      t.turn(360.0 / vertices);
    }
  }

  public static void triangles(Turtle t, int n, int c, int d) {
    for (int i = 0; i < n; i++) {
      regularPolygon(t, 3, i * c);
      t.turn(d);
    }
  }

  public static void polygons(Turtle t, int n, int e) {
    for (int i = 3; i < n; i++) {
      regularPolygon(t, i, e);
    }
  }

  public static void random(Turtle t) {
    int n = 50;
    int magnitude = 30;
    int maxPenSize = 10;
    Random rand = new Random();
    for (int i = 0; i < n; i++) {
      t.penSize(maxPenSize * rand.nextDouble());
      t.move(magnitude * rand.nextDouble());
      t.turn(90 * rand.nextDouble());
    }
  }
}
