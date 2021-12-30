package com.moai.cw.util;

public class Direction {
  public static enum DIRECTION {
    LEFT(0),
    RIGHT(1),
    ;

    private final int value;

    private DIRECTION(final int value) {
      this.value = value;
    }

    public int get() {
      return value;
    }
  }
}
