package graph;

import java.util.ArrayList;

import data.DataManager;
import data.DataManager.DataType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;
import util.Rect;
import util.HSVColor;

class DataClass {
  public String value;
  public double amount;
  public double ratio;
  public HSVColor color;
}

public class CircleGraph extends Graph {
  final static int GRAPHAREA_PADDING = 30;
  final static int GRAPH_SCALE_OFFSET = 10;
  final static int GRAPH_SCALE_HORIZONTAL_ADJUST = -2;
  final static int GRAPH_SCALE_VERTICAL_ADJUST = 5;
  final static int DESCRIPTION_WIDTH = 150;
  final static int DESCRIPTION_MARGIN = 30;
  final static int DESCRIPTION_PADDING = 4;
  final static int DESCRIPTION_ROW_HEIGHT = 20;
  final static int DESCRIPTION_SAMPLE_RECT_SIZE = 10;
  final static int DESCRIPTION_TEXT_MARGIN_LEFT = 8;
  final static int DESCRIPTION_TEXT_VERTICAL_ADJUST = 4;

  public static enum Mode {
    LITERALLY, CLASSIFICATION
  }

  private Mode mode;

  public CircleGraph(Mode mode) {
    this.mode = mode;
  }

  public boolean varidate(DataManager dm) {
    if (mode == Mode.LITERALLY && dm.getTypes()[0] == DataType.STRING) {
      return false;
    }

    return true;
  }

  public void draw(DataManager dm, GraphicsContext gc, Rect rect) {
    DataClass[] result;

    switch (mode) {
    case LITERALLY: {
      double[] data = dm.getNumberData()[0];
      String[] labels;
      if (dm.getData().length >= 2) {
        labels = dm.getData()[1];
      } else {
        labels = null;
      }
      result = analyze(data, labels);
      break;
    }
    case CLASSIFICATION:
    default: {
      String[] data = dm.getData()[0];
      result = classify(data);
      break;
    }
    }

    Rect graphArea = new Rect(rect.x, rect.y, rect.w - DESCRIPTION_WIDTH, rect.h);
    Rect descriptionArea = new Rect(rect.x + rect.w - DESCRIPTION_WIDTH, rect.y, DESCRIPTION_WIDTH, rect.h);

    double radius = Math.min(graphArea.w, graphArea.h) / 2 - GRAPHAREA_PADDING;
    double centerX = rect.x + graphArea.w / 2;
    double centerY = rect.y + graphArea.h / 2;

    gc.setStroke(Color.rgb(115, 133, 150));
    gc.setLineWidth(1.0);
    gc.setTextAlign(TextAlignment.LEFT);

    gc.strokeOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    gc.strokeRect(descriptionArea.x + DESCRIPTION_MARGIN, descriptionArea.y + DESCRIPTION_MARGIN,
        descriptionArea.w - DESCRIPTION_MARGIN * 2, DESCRIPTION_ROW_HEIGHT * result.length + DESCRIPTION_PADDING * 2);

    double sum = 0;
    for (int i = 0; i < result.length; i++) {
      DataClass dc = result[i];
      int[] color = dc.color.getRGB();
      gc.setFill(Color.rgb(color[0], color[1], color[2]));
      gc.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, (1 - sum) * 360 + 90, -dc.ratio * 360,
          ArcType.ROUND);
      gc.setTextAlign((sum + dc.ratio / 2 > 0.5 ? TextAlignment.RIGHT : TextAlignment.LEFT));
      gc.strokeText(mode == Mode.LITERALLY ? Double.toString(dc.amount) : dc.value,
          centerX + (radius + GRAPH_SCALE_OFFSET) * Math.cos(Math.PI * 2 * ((sum + dc.ratio / 2) - 0.25))
              + GRAPH_SCALE_HORIZONTAL_ADJUST,
          centerY + (radius + GRAPH_SCALE_OFFSET) * Math.sin(Math.PI * 2 * ((sum + dc.ratio / 2) - 0.25))
              + GRAPH_SCALE_VERTICAL_ADJUST);
      sum += dc.ratio;

      gc.fillRect(descriptionArea.x + DESCRIPTION_MARGIN + DESCRIPTION_PADDING,
          descriptionArea.y + DESCRIPTION_MARGIN + DESCRIPTION_PADDING + DESCRIPTION_ROW_HEIGHT * i
              + (DESCRIPTION_ROW_HEIGHT - DESCRIPTION_SAMPLE_RECT_SIZE) / 2,
          DESCRIPTION_SAMPLE_RECT_SIZE, DESCRIPTION_SAMPLE_RECT_SIZE);
      gc.strokeRect(descriptionArea.x + DESCRIPTION_MARGIN + DESCRIPTION_PADDING,
          descriptionArea.y + DESCRIPTION_MARGIN + DESCRIPTION_PADDING + DESCRIPTION_ROW_HEIGHT * i
              + (DESCRIPTION_ROW_HEIGHT - DESCRIPTION_SAMPLE_RECT_SIZE) / 2,
          DESCRIPTION_SAMPLE_RECT_SIZE, DESCRIPTION_SAMPLE_RECT_SIZE);
      gc.setTextAlign(TextAlignment.LEFT);
      gc.strokeText(dc.value,
          descriptionArea.x + DESCRIPTION_MARGIN + DESCRIPTION_PADDING + DESCRIPTION_SAMPLE_RECT_SIZE
              + DESCRIPTION_TEXT_MARGIN_LEFT,
          descriptionArea.y + DESCRIPTION_MARGIN + DESCRIPTION_PADDING + DESCRIPTION_ROW_HEIGHT * i
              + DESCRIPTION_ROW_HEIGHT / 2 + DESCRIPTION_TEXT_VERTICAL_ADJUST);
    }
  }

  private DataClass[] analyze(double[] data, String[] labels) {
    DataClass[] arr = new DataClass[data.length];

    double sum = 0;
    for (int i = 0; i < data.length; i++) {
      DataClass dc = new DataClass();
      dc.amount = data[i];
      if (labels != null && i < labels.length) {
        dc.value = labels[i];
      } else {
        dc.value = Integer.toString(i + 1);
      }
      arr[i] = dc;
      sum += data[i];
    }

    for (int i = 0; i < arr.length; i++) {
      DataClass dc = arr[i];
      dc.ratio = (double) dc.amount / sum;
      int index;
      if (arr.length % 2 == 0) {
        index = (2 * i) % arr.length / 2 + (i >= arr.length / 2 ? 1 : 0);
      } else {
        index = (2 * i) % arr.length;
      }
      dc.color = new HSVColor((360.0 / arr.length) * index, 180, 200);
    }

    return arr;
  }

  private DataClass[] classify(String[] data) {
    ArrayList<DataClass> list = new ArrayList<>();

    for (String v : data) {
      boolean exists = false;
      for (DataClass dc : list) {
        if (v.equals(dc.value)) {
          dc.amount++;
          exists = true;
          break;
        }
      }
      if (!exists) {
        DataClass dc = new DataClass();
        dc.value = v;
        dc.amount = 1;
        list.add(dc);
      }
    }

    DataClass[] sorted = list.stream().sorted((a, b) -> (int) Math.floor(b.amount - a.amount))
        .toArray(DataClass[]::new);

    for (int i = 0; i < sorted.length; i++) {
      DataClass dc = sorted[i];
      dc.ratio = (double) dc.amount / data.length;
      int index;
      if (sorted.length % 2 == 0) {
        index = (2 * i) % sorted.length / 2 + (i >= sorted.length / 2 ? 1 : 0);
      } else {
        index = (2 * i) % sorted.length;
      }
      dc.color = new HSVColor((360.0 / sorted.length) * index, 180, 200);
    }

    return sorted;
  }
}
