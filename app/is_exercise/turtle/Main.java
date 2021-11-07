import drag.DragManager;
import javafx.application.Application;
import javafx.stage.Stage;
import manager.TurtleManager;
import operation.Operation;
import turtle.Turtle;
import window.WindowManager;

public class Main extends Application {
  @Override
  public void start(Stage st) {
    WindowManager wm = new WindowManager(st);
    TurtleManager tm = new TurtleManager();

    Operation.load();

    // Turtle t1 = new Turtle(200, 200);
    // Operation.square(t1);
    // tm.add(t1);

    // Turtle t2 = new Turtle(400, 100);
    // Operation.triangles(t2, 40, 2, 20);
    // tm.add(t2);

    // Turtle t3 = new Turtle(400, 400);
    // Operation.polygons(t3, 10, 80);
    // tm.add(t3);

    Turtle t4 = new Turtle(30, 100);
    Operation.symbols(t4, 200);
    tm.add(t4);

    tm.draw(wm.getGC());
    wm.setOnRender(() -> {
      tm.draw(wm.getGC());
    });

    DragManager dm = new DragManager(tm, wm);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
