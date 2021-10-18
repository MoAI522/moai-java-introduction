package graph;

import data.DataManager;
import javafx.scene.canvas.GraphicsContext;
import util.Rect;

public class GraphManager {
  private Graph[] graphs;
  private int graphIndex;

  public GraphManager() {
    graphs = new Graph[3];
    graphs[0] = new LineGraph();
    graphs[1] = new CircleGraph(CircleGraph.Mode.LITERALLY);
    graphs[2] = new CircleGraph(CircleGraph.Mode.CLASSIFICATION);
    graphIndex = 0;
  }

  public void drawGraph(DataManager dm, GraphicsContext gc, Rect rect) {
    graphs[graphIndex].draw(dm, gc, rect);
  }

  public void setGraphIndex(int graphIndex) {
    if (graphIndex < 0 || graphIndex >= graphs.length)
      return;
    this.graphIndex = graphIndex;
  }
}
