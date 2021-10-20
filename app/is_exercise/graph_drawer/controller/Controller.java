package controller;

import java.io.File;

import data.DataManager;
import graph.GraphManager;
import javafx.stage.FileChooser;
import ui.WindowManager;

public class Controller {
  private DataManager dm;
  private GraphManager gm;
  private WindowManager wm;
  private Runnable render;

  public Controller(DataManager dm, GraphManager gm, WindowManager wm, Runnable render) {
    this.dm = dm;
    this.gm = gm;
    this.wm = wm;
    this.render = render;
  }

  public void openFile() {
    File file = wm.openFileChooser();
  }

  public void toggleGraph(int index) {
    gm.setGraphIndex(index);
    render.run();
  }
}
