package com.moai.cw.util;

public class GraphicObject {
  public static enum TYPE {
    TEXTURE,
    COLOR
  }

  public TYPE type;
  public DVector2 position, scale;
  public char textureIndex;
  public CVector2 uv, size;
  public Color color;

  public GraphicObject(DVector2 position, DVector2 scale, char textureIndex, CVector2 uv, CVector2 size) {
    type = TYPE.TEXTURE;
    this.position = position;
    this.scale = scale;
    this.textureIndex = textureIndex;
    this.uv = uv;
    this.size = size;
  }

  public GraphicObject(DVector2 position, DVector2 size, Color color) {
    type = TYPE.COLOR;
    this.position = position;
    this.scale = size;
    this.color = color;
  }
}
