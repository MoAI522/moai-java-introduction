package graph;

import java.util.Arrays;

import data.DataManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.Rect;

class AnalyzationResult {
  public int vBegin, vEnd, vStep, hBegin, hEnd, hStep;
}

public class LineGraph extends Graph {
  final static double TOP_PADDING = 10;
  final static double V_SCALE_SPACE = 30;
  final static double H_SCALE_SPACE = 20;
  final static double TEXT_ADJUSTMENT_X = -5;
  final static double TEXT_ADJUSTMENT_Y = 20;

  public void draw(DataManager dm, GraphicsContext gc, Rect rect) {
    String[][] strData = dm.getData();
    int[][] data = new int[strData.length][];
    for (int i = 0; i < strData.length; i++) {
      data[i] = new int[strData[i].length];
      for (int j = 0; j < strData[i].length; j++) {
        data[i][j] = Integer.parseInt(strData[i][j]);
      }
    }
    AnalyzationResult result = analyze(data);

    gc.setStroke(Color.BLACK);
    gc.strokeLine(rect.x + V_SCALE_SPACE, rect.y + TOP_PADDING, rect.x + V_SCALE_SPACE,
        rect.y + rect.h - H_SCALE_SPACE);
    gc.strokeLine(rect.x + rect.w, rect.y + TOP_PADDING, rect.x + rect.w, rect.y + rect.h - H_SCALE_SPACE);

    int vLineNumber = ((result.vEnd - result.vBegin) / result.vStep);
    double vStepInPx = (rect.h - H_SCALE_SPACE - TOP_PADDING) / vLineNumber;
    for (int v = result.vBegin, i = 0; i <= vLineNumber; v += result.vStep, i++) {
      gc.strokeText(Integer.valueOf(v).toString(), rect.x, rect.y + TOP_PADDING + vStepInPx * (vLineNumber - i));
      gc.strokeLine(rect.x + V_SCALE_SPACE, rect.y + TOP_PADDING + vStepInPx * (vLineNumber - i), rect.x + rect.w,
          rect.y + TOP_PADDING + vStepInPx * (vLineNumber - i));
    }

    double hStepInPx = (rect.w - V_SCALE_SPACE) / (data.length - 1);
    int hScaleStep = Math.floorDiv(data.length, 10) + 1;
    for (int i = 0, j = 1; i < Math.min(9, data.length / hScaleStep); i++, j += hScaleStep) {
      gc.strokeText(Integer.valueOf(j).toString(),
          rect.x + V_SCALE_SPACE + hStepInPx * (i * hScaleStep) + TEXT_ADJUSTMENT_X,
          rect.y + rect.h - H_SCALE_SPACE + TEXT_ADJUSTMENT_Y);
    }
    gc.strokeText(Integer.valueOf(data.length).toString(), rect.x + rect.w + TEXT_ADJUSTMENT_X,
        rect.y + rect.h - H_SCALE_SPACE + TEXT_ADJUSTMENT_Y);

    double dataPxRatio = (rect.h - H_SCALE_SPACE - TOP_PADDING) / (result.vEnd - result.vBegin);
    for (int[] row : data) {
      for (int i = 1; i < row.length; i++) {
        gc.setStroke(Color.BLUE);
        gc.strokeLine(rect.x + V_SCALE_SPACE + hStepInPx * (i - 1),
            rect.y + rect.h - H_SCALE_SPACE - (row[i - 1] - result.vBegin) * dataPxRatio,
            rect.x + V_SCALE_SPACE + hStepInPx * i,
            rect.y + rect.h - H_SCALE_SPACE - (row[i] - result.vBegin) * dataPxRatio);
      }
    }
  }

  private static AnalyzationResult analyze(int[][] data) {
    AnalyzationResult result = new AnalyzationResult();
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

    int range = max - min;
    result.vStep = 1;
    while (true) {
      if (range / result.vStep + 1 < 10)
        break;
      result.vStep *= 5;
      if (range / result.vStep + 1 < 10)
        break;
      result.vStep *= 2;
    }
    result.vBegin = min - (min % result.vStep);
    result.vEnd = max + result.vStep - (max % result.vStep);

    result.hBegin = 1;
    result.hEnd = maxLength;
    result.hStep = Math.floorDiv(maxLength, 10);

    return result;
  }
}
