package ui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class GVMenuBar extends MenuBar {
  private Menu fileMenu;

  public MenuItem fileNew;
  public MenuItem fileImport;

  public GVMenuBar() {
    fileMenu = new Menu("File");
    fileNew = new MenuItem("New Project");
    fileMenu.getItems().add(fileNew);
    fileImport = new MenuItem("Import data");
    fileMenu.getItems().add(fileImport);
    this.getMenus().add(fileMenu);
  }
}
