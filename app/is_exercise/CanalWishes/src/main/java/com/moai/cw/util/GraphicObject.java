package com.moai.cw.util;

public class GraphicObject {
  public DVector2 position, scale;
  public char textureIndex;
  public CVector2 uv, size;

  public GraphicObject(DVector2 position, DVector2 scale, char textureIndex, CVector2 uv, CVector2 size) {
    this.position = position;
    this.scale = scale;
    this.textureIndex = textureIndex;
    this.uv = uv;
    this.size = size;
  }
}
