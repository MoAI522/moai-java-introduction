package com.moai.cw.store;

import com.moai.cw.Constants;

public class FieldStore {
  private int playerHP;
  private boolean[] credits;

  public FieldStore() {
    playerHP = Constants.PLAYER_HP_MAX;
    credits = new boolean[Constants.CREDITS_NUMBER];
  }

  public void changePlayerHP(int diff) {
    playerHP += diff;
  }

  public void achieveCredit(int index) {
    if (index < credits.length) {
      credits[index] = true;
    }
  }

  public int getPlayerHP() {
    return playerHP;
  }

  public boolean hasCredit(int index) {
    if (index < credits.length) {
      return credits[index];
    } else {
      return false;
    }
  }
}
