package ui;

import java.util.Arrays;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.Rect;

class AnalyzationResult {
  public int vBegin, vEnd, vStep, hBegin, hEnd, hStep;
}

public class LineGraph {

  public static void draw(int[] data, GraphicsContext gc, Rect rect) {
    AnalyzationResult result = analyze(data);

    gc.setStroke(Color.BLACK);
    gc.strokeLine(rect.x + 40, rect.y, rect.x + 40, rect.y + rect.h - 40);
  }

  private static AnalyzationResult analyze(int[] data) {
    AnalyzationResult result = new AnalyzationResult();
    int min = data[0], max = data[0];
    for (int v : Arrays.copyOfRange(data, 1, data.length - 1)) {
      if (min > v) {
        min = v;
      }
      if (max < v) {
        max = v;
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
    result.hEnd = data.length;
    result.hStep = Math.floorDiv(data.length, 10);

    return result;
  }
}
