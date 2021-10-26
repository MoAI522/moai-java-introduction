package graph;

import java.util.Arrays;

import data.DataManager;
import data.DataManager.DataType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import util.HSVColor;
import util.Rect;

class LGAnalyzationResult {
  public int vBegin, vEnd, vStep, hBegin, hEnd, hStep;
}

public class LineGraph extends Graph {
  final static double TOP_PADDING = 10;
  final static double V_SCALE_SPACE = 40;
  final static double H_SCALE_SPACE = 20;
  final static double H_SCALE_LENGTH = 5;
  final static double TEXT_ADJUSTMENT_X = -5;
  final static double TEXT_ADJUSTMENT_Y = 20;

  public boolean varidate(DataManager dm) {
    DataType[] types = dm.getTypes();
    for (DataType type : types)
      if (type == DataType.STRING)
        return false;

    return true;
  }

  public void draw(DataManager dm, GraphicsContext gc, Rect rect) {
    double[][] data = dm.getNumberData();

    LGAnalyzationResult result = analyze(data);

    gc.setStroke(Color.rgb(115, 133, 150));
    gc.setLineWidth(1.0);
    gc.setTextAlign(TextAlignment.LEFT);

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
    for (int i = 0; i < result.hEnd; i++) {
      double x = rect.x + V_SCALE_SPACE + hStepInPx * i;
      gc.strokeLine(x, rect.y + rect.h - H_SCALE_SPACE, x, rect.y + rect.h - H_SCALE_SPACE + H_SCALE_LENGTH);
      if (i % hScaleStep == 0 && result.hEnd - i >= hScaleStep) {
        gc.strokeText(Integer.valueOf(i + 1).toString(), x + TEXT_ADJUSTMENT_X,
            rect.y + rect.h - H_SCALE_SPACE + TEXT_ADJUSTMENT_Y);
      }
    }
    gc.strokeText(Integer.valueOf(result.hEnd).toString(), rect.x + rect.w + TEXT_ADJUSTMENT_X,
        rect.y + rect.h - H_SCALE_SPACE + TEXT_ADJUSTMENT_Y);

    double dataPxRatio = (rect.h - H_SCALE_SPACE - TOP_PADDING) / (result.vEnd - result.vBegin);
    double hueStep = (double) 360 / data.length;
    for (int i = 0; i < data.length; i++) {
      double[] row = data[i];
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

  private static LGAnalyzationResult analyze(double[][] data) {
    LGAnalyzationResult result = new LGAnalyzationResult();
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

    double range = max - min;
    result.vStep = 1;
    while (true) {
      if (range / result.vStep < 9)
        break;
      result.vStep *= 5;
      if (range / result.vStep < 9)
        break;
      result.vStep *= 2;
    }
    result.vBegin = (int) Math.floor(min - (min % result.vStep));
    result.vEnd = (int) Math.floor(max + result.vStep - (max % result.vStep));

    result.hBegin = 1;
    result.hEnd = maxLength;
    result.hStep = Math.floorDiv(maxLength, 10);

    return result;
  }
}
