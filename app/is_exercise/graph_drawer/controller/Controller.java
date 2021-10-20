package controller;

import java.io.File;

import data.DataManager;
import graph.GraphManager;
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

    this.wm.setOpenFileFunc(this::openFile);
    this.wm.setToggleGraphFunc(this::toggleGraph);
  }

  public void openFile() {
    File file = wm.openFileChooser();
    if (file == null)
      return;
    wm.setTitle(file.getName() + " - Graph Viewer");
    dm.loadData(file);
    render.run();
  }

  public void toggleGraph(int index) {
    gm.setGraphIndex(index);
    render.run();
  }
}
