import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public void start(Stage st) throws Exception {
    Group root = new Group();

    Scene scene = new Scene(root, 400, 400, Color.BLACK);
    st.setTitle("window test");
    st.setScene(scene);
    st.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}