package graph;

import java.util.Arrays;

import data.DataManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import util.Rect;

class RCAnalyzationResult {
  public int vEnd, vStep, vScaleNumber, maxLength;
}

public class RaderChart extends Graph {
  final static int PADDING = 30;
  final static int SCALE_OFFSET = 10;
  final static int SCALE_VERTICAL_ADJUST = 4;
  final static int VERTICAL_SCALE_MARGIN_LEFT = 12;

  public void draw(DataManager dm, GraphicsContext gc, Rect rect) {
    String[][] strData = dm.getData();
    int[][] data = new int[strData.length][];
    for (int i = 0; i < strData.length; i++) {
      data[i] = new int[strData[i].length];
      for (int j = 0; j < strData[i].length; j++) {
        data[i][j] = Integer.parseInt(strData[i][j]);
      }
    }
    RCAnalyzationResult result = analyze(data);

    double radius = Math.min(rect.w, rect.h) / 2 - PADDING;
    double centerX = rect.x + rect.w / 2;
    double centerY = rect.y + rect.h / 2;

    gc.setStroke(Color.BLACK);
    gc.setTextAlign(TextAlignment.LEFT);
    for (int i = 0; i <= result.vScaleNumber; i++) {
      if (i > 0) {
        drawRegularPolygon(gc, result.maxLength, centerX, centerY, radius * ((double) i / result.vScaleNumber));
      }
      gc.strokeText(Integer.toString(result.vStep * i), centerX + VERTICAL_SCALE_MARGIN_LEFT,
          centerY - radius * ((double) i / result.vScaleNumber));
    }

    double radiusRatio = radius / result.vEnd;
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
        int value;
        if (i >= data[j].length)
          value = 0;
        else
          value = data[j][i];
        x[j][i] = centerX + radiusRatio * value * c;
        y[j][i] = centerY + radiusRatio * value * s;
      }
    }

    for (int i = 0; i < data.length; i++) {
      x[i][result.maxLength] = x[i][0];
      y[i][result.maxLength] = y[i][0];
      gc.setFill();
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

  private static RCAnalyzationResult analyze(int[][] data) {
    RCAnalyzationResult result = new RCAnalyzationResult();
    int min = data[0][0], max = data[0][0];
    int maxLength = 0;
    for (int[] row : data) {
      if (maxLength < row.length) {
        maxLength = row.length;
      }
      for (int v : Arrays.copyOfRange(row, 1, row.length)) {
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
    result.vEnd = max + result.vStep - (max % result.vStep);
    result.vScaleNumber = (int) Math.ceil((double) max / result.vStep);

    result.maxLength = maxLength;

    return result;
  }
}
