package graph;

import data.DataManager;
import javafx.scene.canvas.GraphicsContext;
import util.Rect;

abstract public class Graph {
  abstract public boolean varidate(DataManager dm);

  abstract public void draw(DataManager dm, GraphicsContext gc, Rect rect);
}
