package com.moai.cw.util;

public class Color {
  public double r, g, b, a;

  public Color(double r, double g, double b, double a) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
  }

  public Color(double r, double g, double b) {
    this(r, g, b, 1);
  }
}
