package com.moai.cw.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnemyData {
  @JsonProperty("id")
  private int id;
  @JsonProperty("class")
  private String className;
  @JsonProperty("type")
  private int type;
  @JsonProperty("position")
  private List<Integer> position;
  @JsonProperty("direction")
  private String direction;

  public String getClassName() {
    return className;
  }

  public int getType() {
    return type;
  }

  public List<Integer> getPosition() {
    return position;
  }

  public String getDirection() {
    return direction;
  }
}