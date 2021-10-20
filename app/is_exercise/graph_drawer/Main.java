import data.DataManager;
import graph.GraphManager;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.WindowManager;
import util.Rect;
import controller.Controller;

public class Main extends Application {
  private DataManager dm;
  private WindowManager wm;
  private GraphManager gm;
  private Controller ctr;

  @Override
  public void start(Stage st) throws Exception {
    dm = new DataManager();
    dm.loadData("./datas/data1.txt");

    gm = new GraphManager();

    ctr = new Controller(dm, gm, () -> {
      render(st.widthProperty().intValue(), st.heightProperty().intValue());
    });

    wm = new WindowManager(st, ctr);
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

    gm.drawGraph(dm, wm.getGc(),
        new Rect(padding, menuMargin + padding, w - padding * 2, h - padding * 2 - menuMargin));
  }

  public static void main(String[] args) {
    launch(args);
  }
}
