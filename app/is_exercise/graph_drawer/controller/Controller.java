package controller;

import data.DataManager;
import graph.GraphManager;

public class Controller {
  private DataManager dm;
  private GraphManager gm;
  private Runnable render;

  public Controller(DataManager dm, GraphManager gm, Runnable render) {
    this.dm = dm;
    this.gm = gm;
    this.render = render;
  }

  public void openFile() {
    System.out.println("open file");
  }

  public void toggleGraph(int index) {
    gm.setGraphIndex(index);
  }
}
