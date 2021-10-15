package graph;

import data.DataManager;
import javafx.scene.canvas.GraphicsContext;
import util.Rect;

public class GraphManager {
  private Graph[] graphs;

  public GraphManager() {
    graphs = new Graph[3];
    graphs[0] = new LineGraph();
    graphs[1] = new CircleGraph(CircleGraph.Mode.LITERALLY);
    graphs[2] = new CircleGraph(CircleGraph.Mode.CLASSIFICATION);
  }

  public void drawGraph(DataManager dm, GraphicsContext gc, Rect rect, int index) {
    if (index < 0 || index >= graphs.length)
      return;
    graphs[index].draw(dm, gc, rect);
  }
}
