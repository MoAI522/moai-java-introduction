import javafx.application.Application;
import javafx.stage.Stage;
import turtle.Turtle;
import window.WindowManager;

public class Main extends Application {
  @Override
  public void start(Stage st) {
    WindowManager wm = new WindowManager(st);

    Turtle t = new Turtle(200, 200);
    square(t);
    t.paint(wm.getGC());
  }

  private void square(Turtle t) {
    for (int i = 0; i < 4; i++) {
      t.move(50);
      t.turn(90);
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
