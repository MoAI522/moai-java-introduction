import javax.swing.JFrame;
import javax.swing.JPanel;

import javafx.geometry.Rectangle2D;

import java.awt.Graphics;
import java.awt.Color;

public class Assignment5 extends JPanel {
  @Override
  public void paintComponent(Graphics g) {
    int ww = 400, wh = 300;
    for (int type = 0; type < 4; type++) {
      switch (type) {
      case 0: {
        Color[] colors = { Color.BLUE, Color.GREEN, Color.MAGENTA };
        for (int i = 0; i < colors.length; i++) {
          g.setColor(colors[i]);
          g.fillOval(50 + 60 * i, 50, 50, 50);
        }
        break;
      }
      case 1: {
        Color[] colors = { new Color(0.2f, 0f, 0.8f, 0.5f), new Color(0.8f, 0f, 0.2f, 0.5f) };
        for (int i = 0; i < colors.length; i++) {
          g.setColor(colors[i]);
          g.fillOval(ww + 50 + 50 * i, 50, 80, 80);
        }
        break;
      }
      case 2: {
        Color[] colors = { Color.GREEN, Color.BLUE, Color.YELLOW };
        for (int i = 0; i < colors.length; i++) {
          g.setColor(colors[i]);
          g.fillOval(50 + (30 / 2 * i), wh + 50 + (30 / 2 * i), 80 - 30 * i, 80 - 30 * i);
        }
        break;
      }
      case 3: {
        double w = 100, h = 100;
        Color[] colors = { Color.YELLOW, Color.GREEN, Color.BLUE, Color.YELLOW };
        Rectangle2D[] rects = { new Rectangle2D(150, 50, w, h / 2), new Rectangle2D(100, 80, w, h),
            new Rectangle2D(130, 110, w, h), new Rectangle2D(150, 100, w, h / 2) };
        for (int i = 0; i < colors.length; i++) {
          g.setColor(colors[i]);
          g.fillRect(ww + (int) rects[i].getMinX(), wh + (int) rects[i].getMinY(), (int) rects[i].getWidth(),
              (int) rects[i].getHeight());
        }
        break;
      }
      }
    }
  }

  public static void main(String[] args) {
    JFrame app = new JFrame();
    app.add(new Assignment5());
    app.setSize(800, 600);
    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    app.setVisible(true);
  }
}
