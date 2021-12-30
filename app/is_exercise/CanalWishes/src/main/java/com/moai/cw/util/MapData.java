package com.moai.cw.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MapData {
  @JsonProperty("mapSize")
  private List<Integer> mapSize;
  @JsonProperty("tiles")
  private List<List<Integer>> tiles;
  @JsonProperty("enemies")
  private List<EnemyData> enemies;

  public List<Integer> getMapSize() {
    return mapSize;
  }

  public List<List<Integer>> getTiles() {
    return tiles;
  }

  public List<EnemyData> getEnemies() {
    return enemies;
  }
}
