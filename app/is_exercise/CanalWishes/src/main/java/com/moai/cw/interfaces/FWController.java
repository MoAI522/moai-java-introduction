package com.moai.cw.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import com.moai.cw.util.GraphicObject;

public interface FWController {
  public void draw(ArrayList<GraphicObject> objects, HashMap<String, String> debugInfo);

  public void playSound(int index);

  public void stopSound(int index);
}
