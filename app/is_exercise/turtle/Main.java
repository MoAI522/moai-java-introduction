import drag.DragManager;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import manager.TurtleManager;
import operation.Operation;
import turtle.Turtle;
import window.WindowManager;

public class Main extends Application {
  private static String[] args;

  @Override
  public void start(Stage st) {
    WindowManager wm = new WindowManager(st);
    TurtleManager tm = new TurtleManager();

    Operation.load();

    boolean isNothing = true;
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
      case "0": {
        Turtle t = new Turtle(200, 200);
        t.penColor(Color.BLUE);
        Operation.square(t);
        tm.add(t);
        isNothing = false;
        break;
      }
      case "1": {
        Turtle t = new Turtle(200, 200);
        t.penColor(Color.RED);
        Operation.triangles(t, 40, 2, 20);
        tm.add(t);
        isNothing = false;
        break;
      }
      case "2": {
        Turtle t = new Turtle(200, 300);
        t.penColor(Color.YELLOWGREEN);
        Operation.polygons(t, 10, 80);
        tm.add(t);
        isNothing = false;
        break;
      }
      case "3": {
        Turtle t = new Turtle(200, 200);
        t.penColor(Color.GREEN);
        Operation.tusSymbol(t, 200);
        tm.add(t);
        isNothing = false;
        break;
      }
      case "4": {
        Turtle t = new Turtle(30, 100);
        Operation.symbols(t, 200);
        tm.add(t);
        isNothing = false;
        break;
      }
      }
    }

    tm.draw(wm.getGC());
    if (isNothing) {
      wm.setOnRender(() -> {
        wm.getGC().setStroke(Color.BLACK);
        wm.getGC().strokeText(
            "Nothing is selected to draw.\nPlease select something by command-line arguments.\n0:square\n1:triangles\n2:polygons\n3:tus-symbol\n4:three-tus-symbols",
            0, 20);
      });
    } else {
      wm.setOnRender(() -> {
        tm.draw(wm.getGC());
      });
    }

    DragManager dm = new DragManager(tm, wm);
  }

  public static void main(String[] args) {
    Main.args = args;
    launch(args);
  }
}
