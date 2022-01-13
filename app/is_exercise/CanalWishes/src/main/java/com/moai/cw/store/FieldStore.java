package com.moai.cw.store;

import com.moai.cw.Constants;

public class FieldStore {
  private int playerHP;
  private boolean[] credits;
  private boolean gameOver, gameClear;

  public FieldStore() {
    playerHP = Constants.PLAYER_HP_MAX;
    credits = new boolean[Constants.CREDITS_NUMBER];
    gameOver = false;
    gameClear = false;
  }

  public void changePlayerHP(int diff) {
    playerHP += diff;
    if (playerHP <= 0) {
      playerHP = 0;
      gameOver = true;
    }
  }

  public void achieveCredit(int index) {
    if (index < credits.length) {
      credits[index] = true;

      int i;
      for (i = 0; i < credits.length; i++) {
        if (!credits[i])
          break;
      }
      if (i == credits.length)
        gameClear = true;
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

  public boolean isGameOver() {
    return gameOver;
  }

  public boolean isGameClear() {
    return gameClear;
  }
}
