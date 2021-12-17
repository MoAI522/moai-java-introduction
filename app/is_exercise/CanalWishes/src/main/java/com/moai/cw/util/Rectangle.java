package com.moai.cw.util;

public class Rectangle {
  public double x0, y0, x1, y1;

  public Rectangle(double x, double y, double w, double h) {
    this.x0 = x;
    this.y0 = y;
    this.x1 = x + w;
    this.y1 = y + h;
  }

  public static boolean intersect(Rectangle r0, Rectangle r1) {
    double dx0 = r1.x0 - r0.x1;
    if (dx0 > 0)
      return false;
    double dx1 = r1.x1 - r0.x0;
    if (dx1 < 0)
      return false;
    double dy0 = r1.y0 - r0.y1;
    if (dy0 > 0)
      return false;
    double dy1 = r1.y1 - r0.y0;
    if (dy1 < 0)
      return false;
    return true;
  }
}
