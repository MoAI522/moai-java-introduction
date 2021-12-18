package com.moai.cw.util;

public class DPosition {
  public double x, y;

  public DPosition(double x, double y) {
    set(x, y);
  }

  public void set(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public DPosition clone() {
    return new DPosition(x, y);
  }

  public DPosition add(DPosition b) {
    return new DPosition(x + b.x, y + b.y);
  }

  public DPosition negate() {
    return new DPosition(-x, -y);
  }
}
