package com.moai.cw.game_object;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moai.cw.Constants;
import com.moai.cw.entity.Block;
import com.moai.cw.scene.Scene;
import com.moai.cw.util.DVector2;
import com.moai.cw.util.EnemyData;
import com.moai.cw.util.FieldObjectData;
import com.moai.cw.util.IVector2;
import com.moai.cw.util.MapData;

public class Stage extends GameObject {
  private MapData mapData;

  public Stage(Scene scene) {
    super(scene, new DVector2(0, 0));
  }

  private void loadFile(String mapID) {
    try {
      InputStreamReader isr = new InputStreamReader(
          Thread.currentThread().getContextClassLoader().getResourceAsStream("map/" + mapID + ".json"));
      BufferedReader br = new BufferedReader(isr);
      String jsonStr = "";
      String line;
      while ((line = br.readLine()) != null)
        jsonStr = jsonStr + "\n" + line;
      br.close();

      ObjectMapper mapper = new ObjectMapper();
      mapData = mapper.readValue(jsonStr, MapData.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void loadStage(String mapID) {
    clearChildren();

    loadFile(mapID);
    for (int i = 0; i < mapData.getMapSize().get(1); i++) {
      for (int j = 0; j < mapData.getMapSize().get(0); j++) {
        if (mapData.getTiles().get(i).get(j) == 0)
          continue;
        new Block(getScene(), this,
            new DVector2(j * Constants.MAPCHIP_SIZE, i * Constants.MAPCHIP_SIZE),
            mapData.getTiles().get(i).get(j) - 1);
      }
    }
    for (int i = 0; i < mapData.getMapSize().get(0); i++) {
      new Block(getScene(), this, new DVector2(i * Constants.MAPCHIP_SIZE, -Constants.MAPCHIP_SIZE), 0);
    }
    for (int i = 0; i < mapData.getMapSize().get(1); i++) {
      new Block(getScene(), this, new DVector2(-Constants.MAPCHIP_SIZE, i * Constants.MAPCHIP_SIZE), 0);
      new Block(getScene(), this,
          new DVector2(mapData.getMapSize().get(0) * Constants.MAPCHIP_SIZE, i * Constants.MAPCHIP_SIZE), 0);
    }

    for (EnemyData enemy : mapData.getEnemies()) {
      new Spawner(getScene(), this, enemy);
    }

    for (FieldObjectData fieldObject : mapData.getFieldObjects()) {
      generateFieldObject(fieldObject);
    }
  }

  private void generateFieldObject(FieldObjectData data) {
    try {
      Class<?> fieldObjectClass = Class.forName(data.getClassName());
      Constructor<?> constructor = fieldObjectClass.getConstructor(Scene.class, GameObject.class, IVector2.class,
          List.class);
      constructor.newInstance(getScene(), this, new IVector2(data.getPosition().get(0), data.getPosition().get(1)),
          data.getParams());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(int dt) {
  }

  public DVector2 getMapSize() {
    return new DVector2(mapData.getMapSize().get(0) * Constants.MAPCHIP_SIZE,
        mapData.getMapSize().get(1) * Constants.MAPCHIP_SIZE);
  }
}
