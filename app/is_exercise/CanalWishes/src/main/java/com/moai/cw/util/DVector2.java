package com.moai.cw.util;

public class DVector2 {
  public double x, y;

  public DVector2(double x, double y) {
    set(x, y);
  }

  public void set(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public DVector2 clone() {
    return new DVector2(x, y);
  }

  public DVector2 add(DVector2 b) {
    return new DVector2(x + b.x, y + b.y);
  }

  public DVector2 multipleScalar(double s) {
    return new DVector2(x * s, y * s);
  }

  public DVector2 negate() {
    return new DVector2(-x, -y);
  }

  public double magnitude() {
    return Math.sqrt(x * x + y * y);
  }

  @Override
  public String toString() {
    return "(" + x + "," + y + ")";
  }
}
