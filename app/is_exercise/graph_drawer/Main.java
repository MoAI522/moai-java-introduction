import data.DataManager;
import graph.GraphManager;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.WindowManager;
import util.Rect;

public class Main extends Application {
  private DataManager dm;
  private WindowManager wm;
  private GraphManager gm;

  @Override
  public void start(Stage st) throws Exception {
    dm = new DataManager();
    dm.loadData("./datas/data1.txt");

    gm = new GraphManager();

    wm = new WindowManager(st);
    wm.initWindow(640, 480);

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

    gm.drawGraph(dm, wm.getGc(), new Rect(padding, menuMargin + padding, w - padding * 2, h - padding * 2 - menuMargin),
        3);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
