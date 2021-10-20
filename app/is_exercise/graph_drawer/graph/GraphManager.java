package graph;

import data.DataManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import util.Rect;

public class GraphManager {
  private Graph[] graphs;
  private int graphIndex;

  public GraphManager() {
    graphs = new Graph[4];
    graphs[0] = new LineGraph();
    graphs[1] = new CircleGraph(CircleGraph.Mode.LITERALLY);
    graphs[2] = new CircleGraph(CircleGraph.Mode.CLASSIFICATION);
    graphs[3] = new RaderChart();
    graphIndex = 0;
  }

  public void drawGraph(DataManager dm, GraphicsContext gc, Rect rect) {
    if (dm.getFilename() == null) {
      gc.setStroke(Color.rgb(115, 133, 150));
      gc.setLineWidth(1.0);
      gc.setTextAlign(TextAlignment.LEFT);
      gc.strokeText("Press \"Open File\" button to choose data file.", rect.x, rect.y);
      return;
    }
    if (!graphs[graphIndex].varidate(dm)) {
      gc.setStroke(Color.rgb(115, 133, 150));
      gc.setLineWidth(1.0);
      gc.setTextAlign(TextAlignment.LEFT);
      gc.strokeText("This data cannot be shown by this graph type.", rect.x, rect.y);
      return;
    }

    graphs[graphIndex].draw(dm, gc, rect);
  }

  public void setGraphIndex(int graphIndex) {
    if (graphIndex < 0 || graphIndex >= graphs.length)
      return;
    this.graphIndex = graphIndex;
  }
}
