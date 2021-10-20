package graph;

import java.util.Arrays;

import data.DataManager;
import data.DataManager.DataType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import util.HSVColor;
import util.Rect;

class RCAnalyzationResult {
  public int vEnd, vStep, vScaleNumber, maxLength;
}

public class RaderChart extends Graph {
  final static int PADDING = 30;
  final static int SCALE_OFFSET = 10;
  final static int SCALE_VERTICAL_ADJUST = 4;
  final static int VERTICAL_SCALE_MARGIN_LEFT = 12;
  final static double OPACITY = 0.3;

  public boolean varidate(DataManager dm) {
    DataType[] types = dm.getTypes();
    for (DataType type : types)
      if (type == DataType.STRING)
        return false;

    return true;
  }

  public void draw(DataManager dm, GraphicsContext gc, Rect rect) {
    double[][] data = dm.getNumberData();
    RCAnalyzationResult result = analyze(data);

    double radius = Math.min(rect.w, rect.h) / 2 - PADDING;
    double centerX = rect.x + rect.w / 2;
    double centerY = rect.y + rect.h / 2;

    gc.setStroke(Color.BLACK);
    gc.setLineWidth(1.0);
    gc.setTextAlign(TextAlignment.LEFT);

    for (int i = 0; i <= result.vScaleNumber; i++) {
      if (i > 0) {
        drawRegularPolygon(gc, result.maxLength, centerX, centerY, radius * ((double) i / result.vScaleNumber));
      }
      gc.strokeText(Integer.toString(result.vStep * i), centerX + VERTICAL_SCALE_MARGIN_LEFT,
          centerY - radius * ((double) i / result.vScaleNumber));
    }

    double dataRadiusRatio = radius / result.vEnd;
    double[][] x = new double[data.length][result.maxLength + 1];
    double[][] y = new double[data.length][result.maxLength + 1];
    for (int i = 0; i < result.maxLength; i++) {
      double c = Math.cos(Math.PI * 2 * ((double) i / result.maxLength - 0.25));
      double s = Math.sin(Math.PI * 2 * ((double) i / result.maxLength - 0.25));
      gc.strokeLine(centerX, centerY, centerX + radius * c, centerY + radius * s);
      gc.setTextAlign(i > result.maxLength / 2 ? TextAlignment.RIGHT : TextAlignment.LEFT);
      gc.strokeText(Integer.toString(i + 1), centerX + (radius + SCALE_OFFSET) * c,
          centerY + (radius + SCALE_OFFSET) * s + SCALE_VERTICAL_ADJUST);

      for (int j = 0; j < data.length; j++) {
        double value;
        if (i >= data[j].length)
          value = 0;
        else
          value = data[j][i];
        x[j][i] = centerX + dataRadiusRatio * value * c;
        y[j][i] = centerY + dataRadiusRatio * value * s;
      }
    }

    gc.setLineWidth(3.0);
    double hueStep = 360.0 / data.length;
    for (int i = 0; i < data.length; i++) {
      x[i][result.maxLength] = x[i][0];
      y[i][result.maxLength] = y[i][0];
      int[] color = new HSVColor(hueStep * i, 200, 230).getRGB();
      gc.setStroke(Color.rgb(color[0], color[1], color[2]));
      gc.strokePolygon(x[i], y[i], result.maxLength);
      gc.setFill(Color.rgb(color[0], color[1], color[2], OPACITY));
      gc.fillPolygon(x[i], y[i], result.maxLength);
    }

  }

  private void drawRegularPolygon(GraphicsContext gc, int vertex, double centerX, double centerY, double radius) {
    double[] x = new double[vertex + 1];
    double[] y = new double[vertex + 1];
    for (int i = 0; i < vertex; i++) {
      x[i] = centerX + radius * Math.cos(Math.PI * 2 * ((double) i / vertex - 0.25));
      y[i] = centerY + radius * Math.sin(Math.PI * 2 * ((double) i / vertex - 0.25));
    }
    x[vertex] = x[0];
    y[vertex] = y[0];
    gc.strokePolygon(x, y, vertex + 1);
  }

  private static RCAnalyzationResult analyze(double[][] data) {
    RCAnalyzationResult result = new RCAnalyzationResult();
    double min = data[0][0], max = data[0][0];
    int maxLength = 0;
    for (double[] row : data) {
      if (maxLength < row.length) {
        maxLength = row.length;
      }
      for (double v : Arrays.copyOfRange(row, 1, row.length)) {
        if (min > v) {
          min = v;
        }
        if (max < v) {
          max = v;
        }
      }
    }

    result.vStep = 1;
    while (true) {
      if (max / result.vStep + 1 < 10)
        break;
      result.vStep *= 5;
      if (max / result.vStep + 1 < 10)
        break;
      result.vStep *= 2;
    }
    result.vEnd = (int) Math.floor(max + result.vStep - (max % result.vStep));
    result.vScaleNumber = (int) Math.ceil((double) max / result.vStep);

    result.maxLength = maxLength;

    return result;
  }
}
