package com.moai.cw.util;

public class IVector2 {
  public int x, y;

  public IVector2(int x, int y) {
    set(x, y);
  }

  public void set(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public IVector2 add(IVector2 b) {
    return new IVector2(x + b.x, y + b.y);
  }

  public IVector2 negate() {
    return new IVector2(-x, -y);
  }
}
