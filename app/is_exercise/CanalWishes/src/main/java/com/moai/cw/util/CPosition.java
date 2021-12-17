package com.moai.cw.util;

public class CPosition {
  public char x, y;

  public CPosition(int x, int y) {
    set(x, y);
  }

  public void set(int x, int y) {
    this.x = (char) x;
    this.y = (char) y;
  }

  public CPosition add(CPosition b) {
    return new CPosition((char) (x + b.x), (char) (y + b.y));
  }

  public CPosition negate() {
    return new CPosition((char) -x, (char) -y);
  }
}
