package operation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import turtle.Turtle;

public class Operation {
  private static ScriptData[] symbolScript;

  public static void load() {
    ArrayList<ScriptData> tempScript = new ArrayList<>();

    File file = new File("./scripts/symbol.dat");
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      String str = br.readLine();
      while (str != null) {
        String[] tokens = str.split(" ");
        str = br.readLine();

        ScriptData.ScriptOperation operation = ScriptData.ScriptOperation.NIL;
        int requiredData = 0;
        switch (tokens[0]) {
        case "TURN":
          operation = ScriptData.ScriptOperation.TURN;
          requiredData = 1;
          break;
        case "JUMP":
          operation = ScriptData.ScriptOperation.JUMP;
          requiredData = 1;
          break;
        case "MOVE":
          operation = ScriptData.ScriptOperation.MOVE;
          requiredData = 1;
          break;
        case "CBEZIER":
          operation = ScriptData.ScriptOperation.CBEZIER;
          requiredData = 5;
          break;
        case "CIRCLE":
          operation = ScriptData.ScriptOperation.CIRCLE;
          requiredData = 1;
          break;
        case "FCIRCLE":
          operation = ScriptData.ScriptOperation.FCIRCLE;
          requiredData = 1;
          break;
        case "SIZE":
          operation = ScriptData.ScriptOperation.SIZE;
          requiredData = 1;
          break;
        }
        if (operation == ScriptData.ScriptOperation.NIL)
          continue;
        if (requiredData > tokens.length - 1)
          continue;

        double[] data = new double[tokens.length - 1];
        for (int i = 1; i < tokens.length; i++) {
          data[i - 1] = Double.parseDouble(tokens[i]);
        }

        tempScript.add(new ScriptData(operation, data));
      }
      br.close();
    } catch (FileNotFoundException e) {
      System.err.println("Could not open the file " + file.getAbsolutePath());
    } catch (IOException e) {
      System.err.println("Could not read the file " + file.getAbsolutePath());
    }

    symbolScript = tempScript.toArray(new ScriptData[tempScript.size()]);
  }

  public static void square(Turtle t) {
    t.penSize(1);
    for (int i = 0; i < 4; i++) {
      t.move(50);
      t.turn(90);
    }
  }

  public static void regularPolygon(Turtle t, int vertices, double size) {
    for (int i = 0; i < vertices; i++) {
      t.move(size);
      t.turn(360.0 / vertices);
    }
  }

  public static void triangles(Turtle t, int n, int c, int d) {
    for (int i = 0; i < n; i++) {
      regularPolygon(t, 3, i * c);
      t.turn(d);
    }
  }

  public static void polygons(Turtle t, int n, int e) {
    for (int i = 3; i < n; i++) {
      regularPolygon(t, i, e);
    }
  }

  public static void tusSymbol(Turtle t, double size) {
    for (int i = 0; i < symbolScript.length; i++) {
      symbolScript[i].operate(t, size);
    }
  }

  public static void symbols(Turtle t, double size) {
    double sideRotDeg = 30;
    double sideRotRad = sideRotDeg / 180 * Math.PI;
    double sideSize = size / (Math.sqrt(2)
        * Math.max(Math.abs(Math.sin(sideRotRad + Math.PI / 4)), Math.abs(Math.cos(sideRotRad + Math.PI / 4))));
    t.turn(-90);
    t.jump(sideSize * Math.sin(sideRotRad));
    t.turn(90 + sideRotDeg);
    t.save();
    t.penColor(Color.RED);
    tusSymbol(t, sideSize);
    t.reset();
    t.turn(90 - sideRotDeg);
    t.jump(sideSize * Math.sin(sideRotRad));
    t.turn(-90);
    t.jump(sideSize * Math.sqrt(2)
        * Math.max(Math.abs(Math.sin(sideRotRad + Math.PI / 4)), Math.abs(Math.cos(sideRotRad + Math.PI / 4))));
    t.save();
    t.penColor(Color.GREEN);
    tusSymbol(t, size);
    t.reset();
    t.jump(size + sideSize * Math.sin(sideRotRad));
    t.turn(-sideRotDeg);
    t.save();
    t.penColor(Color.BLUE);
    tusSymbol(t, sideSize);
    t.reset();
  }

  private static class ScriptData {
    public static enum ScriptOperation {
      TURN, JUMP, MOVE, CBEZIER, CIRCLE, FCIRCLE, SIZE, NIL
    }

    private ScriptOperation operation;
    private double[] data;

    public ScriptData(ScriptOperation operation, double... data) {
      this.operation = operation;
      this.data = data;
    }

    public void operate(Turtle t, double size) {
      switch (operation) {
      case TURN:
        t.turn(data[0]);
        break;
      case JUMP:
        t.jump(data[0] * size);
        break;
      case MOVE:
        t.move(data[0] * size);
        break;
      case CBEZIER:
        t.cbezier(data[4] * size, new Point2D(data[0], data[1]).multiply(size),
            new Point2D(data[2], data[3]).multiply(size));
        break;
      case CIRCLE:
        t.circle(data[0] * size, false);
        break;
      case FCIRCLE:
        t.circle(data[0] * size, true);
        break;
      case SIZE:
        t.penSize(data[0] * size);
        break;
      case NIL:
        break;
      }
    }
  }
}
