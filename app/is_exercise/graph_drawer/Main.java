import data.DataManager;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.LineGraph;
import ui.WindowManager;
import util.Rect;

public class Main extends Application {
  private DataManager dm;
  private WindowManager wm;

  @Override
  public void start(Stage st) throws Exception {
    wm = new WindowManager(st);
    wm.initWindow(640, 480);

    dm = new DataManager();
    dm.loadData("./datas/data1.txt");

    st.widthProperty().addListener((obs, oldVal, newVal) -> {
      render(newVal.intValue(), st.heightProperty().intValue());
    });
    st.heightProperty().addListener((obs, oldVal, newVal) -> {
      render(st.widthProperty().intValue(), newVal.intValue());
    });

    render(st.widthProperty().intValue(), st.heightProperty().intValue());
  }

  private void render(int w, int h) {
    int padding = 20;
    int menuMargin = 30;
    wm.resetCanvas(w, h);
    LineGraph.draw(dm.getData(), wm.getGc(),
        new Rect(padding, menuMargin + padding, w - padding * 2, h - padding * 2 - menuMargin));
  }

  public static void main(String[] args) {
    launch(args);
  }
}
