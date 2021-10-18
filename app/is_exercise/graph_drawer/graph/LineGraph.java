package graph;

import java.util.Arrays;

import data.DataManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.HSVColor;
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

    double hStepInPx = (rect.w - V_SCALE_SPACE) / (result.hEnd - 1);
    int hScaleStep = Math.floorDiv(result.hEnd, 10) + 1;
    for (int i = 0, j = 1; i < Math.min(9, result.hEnd / hScaleStep); i++, j += hScaleStep) {
      gc.strokeText(Integer.valueOf(j).toString(),
          rect.x + V_SCALE_SPACE + hStepInPx * (i * hScaleStep) + TEXT_ADJUSTMENT_X,
          rect.y + rect.h - H_SCALE_SPACE + TEXT_ADJUSTMENT_Y);
    }
    gc.strokeText(Integer.valueOf(result.hEnd).toString(), rect.x + rect.w + TEXT_ADJUSTMENT_X,
        rect.y + rect.h - H_SCALE_SPACE + TEXT_ADJUSTMENT_Y);

    double dataPxRatio = (rect.h - H_SCALE_SPACE - TOP_PADDING) / (result.vEnd - result.vBegin);
    double hueStep = (double) 360 / data.length;
    for (int i = 0; i < data.length; i++) {
      int[] row = data[i];
      int[] color = new HSVColor(hueStep * i, 200, 230).getRGB();
      gc.setStroke(Color.rgb(color[0], color[1], color[2]));
      for (int j = 1; j < row.length; j++) {
        gc.strokeLine(rect.x + V_SCALE_SPACE + hStepInPx * (j - 1),
            rect.y + rect.h - H_SCALE_SPACE - (row[j - 1] - result.vBegin) * dataPxRatio,
            rect.x + V_SCALE_SPACE + hStepInPx * j,
            rect.y + rect.h - H_SCALE_SPACE - (row[j] - result.vBegin) * dataPxRatio);
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
