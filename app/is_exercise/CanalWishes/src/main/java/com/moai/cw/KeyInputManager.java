package com.moai.cw;

import java.util.HashMap;

public class KeyInputManager {
  public static enum KeyCode {
    W, A, S, D, B, N, M, SPACE, ESC
  }

  private HashMap<KeyCode, Integer> keyState;

  public KeyInputManager() {
    keyState = new HashMap<KeyCode, Integer>();
    for (KeyCode keyCode : KeyCode.values()) {
      keyState.put(keyCode, -1);
    }
  }

  public void update() {
    for (KeyCode keyCode : KeyCode.values()) {
      int val = keyState.get(keyCode);
      if (val == -1)
        continue;
      keyState.put(keyCode, val + 1);
    }
  }

  public void onKeyPress(KeyCode keyCode) {
    if (keyCode == null)
      return;
    if (keyState.get(keyCode) != -1)
      return;
    keyState.put(keyCode, 0);
  }

  public void onKeyRelease(KeyCode keyCode) {
    if (keyCode == null)
      return;
    keyState.put(keyCode, -1);
  }

  public int getState(KeyCode keyCode) {
    return keyState.get(keyCode);
  }

  public void reset() {
    for (KeyCode keyCode : KeyCode.values()) {
      keyState.put(keyCode, 0);
    }
  }
}
