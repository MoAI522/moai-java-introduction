package com.moai.cw.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FieldObjectData {
  @JsonProperty("id")
  private String id;
  @JsonProperty("class")
  private String className;
  @JsonProperty("position")
  private List<Integer> position;
  @JsonProperty("params")
  private List<String> params;
  @JsonProperty("_index")
  private int _index;

  public String getClassName() {
    return className;
  }

  public List<Integer> getPosition() {
    return position;
  }

  public List<String> getParams() {
    return params;
  }
}
