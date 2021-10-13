import data.DataManager;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.LineGraph;
import ui.WindowManager;
import util.Rect;

public class Main extends Application {
  @Override
  public void start(Stage st) throws Exception {
    WindowManager wm = new WindowManager(st);
    wm.initWindow();

    DataManager dm = new DataManager();
    dm.loadData("./datas/data1.txt");

    LineGraph.draw(dm.getData(), wm.getGc(), new Rect(10, 10, 600, 400));
  }

  public static void main(String[] args) {
    launch(args);
  }
}
