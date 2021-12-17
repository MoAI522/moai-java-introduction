package com.moai.cw.util;

public class GraphicObject {
  public DPosition position, scale;
  public char textureIndex;
  public CPosition uv, size;

  public GraphicObject(DPosition position, DPosition scale, char textureIndex, CPosition uv, CPosition size) {
    this.position = position;
    this.scale = scale;
    this.textureIndex = textureIndex;
    this.uv = uv;
    this.size = size;
  }
}
