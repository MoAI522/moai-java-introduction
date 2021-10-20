package ui;

import java.io.File;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WindowManager {
  private Stage st;
  private Canvas canvas;
  private GraphicsContext gc;
  private Runnable openFileFunc;
  private IToggleGraphFunc toggleGraphFunc;

  public WindowManager(Stage st) {
    this.st = st;
    Rectangle2D screenSize = Screen.getPrimary().getBounds();
    canvas = new Canvas(screenSize.getWidth(), screenSize.getHeight());
    gc = canvas.getGraphicsContext2D();
  }

  public void initWindow(int w, int h) {
    BorderPane root = new BorderPane();
    root.getChildren().add(canvas);

    FlowPane controllers = new FlowPane();
    Button openFile = new Button("Open File");
    openFile.setOnAction((ActionEvent e) -> {
      if (openFileFunc != null)
        openFileFunc.run();
    });
    ObservableList<String> options = FXCollections.observableArrayList("Line Graph", "Circle Graph(Literally)",
        "Circle Graph(Classify)", "Rader Chart");
    ChoiceBox<String> graphSelector = new ChoiceBox<>(options);
    graphSelector.getSelectionModel().selectedIndexProperty()
        .addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
          if (toggleGraphFunc != null)
            toggleGraphFunc.run(new_val.intValue());
        });
    graphSelector.setValue(options.get(0));
    controllers.getChildren().addAll(openFile, graphSelector);
    root.setTop(controllers);

    canvas.resize(w, h);

    Scene scene = new Scene(root, w, h, Color.WHITE);
    st.setTitle("Graph Viewer");
    st.setScene(scene);
    st.show();
  }

  public void resetCanvas(int w, int h) {
    canvas.resize(w, h);
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  public File openFileChooser() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Data File");
    return fileChooser.showOpenDialog(st);
  }

  public void setTitle(String title) {
    st.setTitle(title);
  }

  public void setOpenFileFunc(Runnable openFileFunc) {
    this.openFileFunc = openFileFunc;
  }

  public void setToggleGraphFunc(IToggleGraphFunc toggleGraphFunc) {
    this.toggleGraphFunc = toggleGraphFunc;
  }

  public GraphicsContext getGc() {
    return gc;
  }
}
