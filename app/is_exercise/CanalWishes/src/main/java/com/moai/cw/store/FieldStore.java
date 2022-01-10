package com.moai.cw.store;

import com.moai.cw.Constants;

public class FieldStore {
  private int playerHP;

  public FieldStore() {
    this.playerHP = Constants.PLAYER_HP_MAX;
  }

  public void changePlayerHP(int diff) {
    playerHP += diff;
  }

  public int getPlayerHP() {
    return playerHP;
  }
}
