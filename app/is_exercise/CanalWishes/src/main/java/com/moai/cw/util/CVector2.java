package com.moai.cw.util;

public class CVector2 {
  public char x, y;

  public CVector2(int x, int y) {
    set(x, y);
  }

  public void set(int x, int y) {
    this.x = (char) x;
    this.y = (char) y;
  }

  public CVector2 add(CVector2 b) {
    return new CVector2((char) (x + b.x), (char) (y + b.y));
  }

  public CVector2 negate() {
    return new CVector2((char) -x, (char) -y);
  }
}
