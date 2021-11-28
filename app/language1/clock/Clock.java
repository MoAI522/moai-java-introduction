import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.awt.Rectangle;
import java.awt.Color;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class Clock extends JPanel implements Runnable {
  Calendar now;
  Thread th;

  Image img_frame;
  Image img_button_default, img_button_active;
  Image img_digital_bg_default, img_digital_bg_active;
  Image img_analog_bg_default, img_analog_bg_active;

  Clip se;

  Rectangle buttonRect;
  Rectangle displayRect;

  int state = 2;
  boolean transitionCompleted = true;
  boolean clickingButton = false;
  boolean clickingDisplay = false;
  double blind = 0;

  public Clock() {
    addMouseListener(new MouseEventListener());
    addMouseMotionListener(new MouseEventListener());

    setOpaque(false);

    img_frame = Toolkit.getDefaultToolkit().getImage("./images/frame.png");
    img_button_default = Toolkit.getDefaultToolkit().getImage("./images/button_default.png");
    img_button_active = Toolkit.getDefaultToolkit().getImage("./images/button_active.png");
    img_digital_bg_default = Toolkit.getDefaultToolkit().getImage("./images/digital_bg_default.png");
    img_digital_bg_active = Toolkit.getDefaultToolkit().getImage("./images/digital_bg_active.png");
    img_analog_bg_default = Toolkit.getDefaultToolkit().getImage("./images/analog_bg_default.png");
    img_analog_bg_active = Toolkit.getDefaultToolkit().getImage("./images/analog_bg_active.png");

    try {
      AudioInputStream ais = AudioSystem.getAudioInputStream(new File("./se/pocket-watch-se.wav"));
      AudioFormat af = ais.getFormat();
      DataLine.Info dataLine = new DataLine.Info(Clip.class, af);
      se = (Clip) AudioSystem.getLine(dataLine);
      se.open(ais);
    } catch (Exception e) {
    }

    buttonRect = new Rectangle(216, 80, 36, 96);
    displayRect = new Rectangle(16, 16, 192, 160);
  }

  public static void main(String[] args) {
    JFrame app = new JFrame();
    Clock clock = new Clock();
    app.add(clock);
    app.setSize(256, 192);
    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    app.setResizable(false);
    app.setLocationRelativeTo(null);
    app.setVisible(true);

    clock.start();
  }

  public void start() {
    if (th == null) {
      th = new Thread(this);
      th.start();
    }
  }

  public void stop() {
    if (th != null) {
      th = null;
    }
  }

  public void run() {
    while (th != null) {
      now = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));
      repaint();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
      }
    }
  }

  public class MouseEventListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      if (!transitionCompleted)
        return;
      if (buttonRect.contains(e.getX(), e.getY())) {
        clickingButton = true;
        repaint();
      } else if (displayRect.contains(e.getX(), e.getY())) {
        clickingDisplay = true;
        repaint();
      }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      if (clickingButton && !buttonRect.contains(e.getX(), e.getY())) {
        clickingButton = false;
        repaint();
      }
      if (clickingDisplay && !displayRect.contains(e.getX(), e.getY())) {
        clickingDisplay = false;
        repaint();
      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      if (clickingButton) {
        if (transitionCompleted) {
          state = (state + 1) % 4;
          transitionCompleted = false;
          Transition transition = new Transition();
          transition.start();

          se.setFramePosition(0);
          se.start();
        }

        clickingButton = false;
        repaint();
      }
      if (clickingDisplay) {
        clickingDisplay = false;
        repaint();
      }
    }
  }

  public void paintComponent(Graphics g) {
    g.drawImage(img_frame, 0, 0, this);
    g.drawImage(clickingButton ? img_button_active : img_button_default, buttonRect.x, buttonRect.y, this);
    switch (state) {
      case 0:
      case 1: {
        g.drawImage(clickingDisplay ? img_digital_bg_active : img_digital_bg_default, displayRect.x, displayRect.y,
            this);
        g.setColor(clickingDisplay ? new Color(56, 143, 111) : new Color(58, 90, 62));
        int lw = 7;
        int x = (int) (112 - lw * 10.5);
        paintNumber(g, now.get(Calendar.HOUR_OF_DAY) / 10, x, lw);
        paintNumber(g, now.get(Calendar.HOUR_OF_DAY) % 10, x + lw * 5, lw);
        g.fillRect(x + lw * 10, 48 + lw * 2, lw, lw);
        g.fillRect(x + lw * 10, 48 + lw * 8, lw, lw);
        paintNumber(g, now.get(Calendar.MINUTE) / 10, x + lw * 12, lw);
        paintNumber(g, now.get(Calendar.MINUTE) % 10, x + lw * 17, lw);
        break;
      }
      case 2:
      case 3: {
        g.drawImage(clickingDisplay ? img_analog_bg_active : img_analog_bg_default, displayRect.x, displayRect.y,
            this);
        Vector2D c = new Vector2D(112, 96);

        {
          double lw = 4, ll = 44;
          int[] xPoints = new int[4], yPoints = new int[4];
          Vector2D dir = new Vector2D(
              Math.cos(Math.PI * 2 * ((double) (now.get(Calendar.MINUTE)) / 60 - 0.25)),
              Math.sin(Math.PI * 2 * ((double) (now.get(Calendar.MINUTE)) / 60 - 0.25)));
          Vector2D normal = new Vector2D(-dir.y, dir.x);
          Vector2D[] p = new Vector2D[4];
          p[0] = c.clone().add(normal.clone().multiplyScalar(lw / 2));
          p[1] = c.clone().add(normal.clone().negate().multiplyScalar(lw / 2));
          p[2] = p[1].clone().add(dir.clone().multiplyScalar(ll));
          p[3] = p[0].clone().add(dir.clone().multiplyScalar(ll));
          for (int i = 0; i < 4; i++) {
            xPoints[i] = (int) Math.round(p[i].x);
            yPoints[i] = (int) Math.round(p[i].y);
          }
          g.setColor(clickingDisplay ? new Color(56, 143, 111)
              : new Color(58, 90,
                  62));
          g.fillPolygon(xPoints, yPoints, 4);
        }

        {
          double lw = 8, ll = 32;
          int[] xPoints = new int[4], yPoints = new int[4];
          Vector2D dir = new Vector2D(
              Math.cos(Math.PI * 2 * ((double) (now.get(Calendar.HOUR) * 60 + now.get(Calendar.MINUTE)) / 720 - 0.25)),
              Math.sin(Math.PI * 2 * ((double) (now.get(Calendar.HOUR) * 60 + now.get(Calendar.MINUTE)) / 720 - 0.25)));
          Vector2D normal = new Vector2D(-dir.y, dir.x);
          Vector2D[] p = new Vector2D[4];
          p[0] = c.clone().add(normal.clone().multiplyScalar(lw / 2));
          p[1] = c.clone().add(normal.clone().negate().multiplyScalar(lw / 2));
          p[2] = p[1].clone().add(dir.clone().multiplyScalar(ll));
          p[3] = p[0].clone().add(dir.clone().multiplyScalar(ll));
          for (int i = 0; i < 4; i++) {
            xPoints[i] = (int) Math.round(p[i].x);
            yPoints[i] = (int) Math.round(p[i].y);
          }
          g.setColor(clickingDisplay ? new Color(104, 181, 142) : new Color(93, 149, 94));
          g.fillPolygon(xPoints, yPoints, 4);
        }
        break;
      }
    }

    g.setColor(new Color(64, 64, 64));
    g.fillRect(displayRect.x, displayRect.y, displayRect.width, (int) Math.round(displayRect.height / 2 * blind));
    g.fillRect(displayRect.x, (int) Math.round(displayRect.y + displayRect.height / 2 * (2 - blind)), displayRect.width,
        (int) Math.round(displayRect.height / 2 * blind));
  }

  private void paintNumber(Graphics g, int number, int x, int lw) {
    int y = 48;

    if (number == 1) {
      g.fillRect((int) (x + lw * 1.5), y, lw, lw * 11);
      return;
    }

    Rectangle[] segments = { new Rectangle(x, y, lw, lw * 6), new Rectangle(x, y + lw * 5, lw, lw * 6),
        new Rectangle(x, y, lw * 4, lw), new Rectangle(x, y + lw * 5, lw * 4, lw),
        new Rectangle(x, y + lw * 10, lw * 4, lw), new Rectangle(x + lw * 3, y, lw, lw * 6),
        new Rectangle(x + lw * 3, y + lw * 5, lw, lw * 6) };
    int[][] indexes = { { 0, 4, 5, 6, 7, 8, 9 }, { 0, 2, 6, 8 }, { 0, 2, 3, 5, 6, 7, 8, 9 }, { 2, 3, 4, 5, 6, 8, 9 },
        { 0, 2, 3, 5, 6, 8 }, { 0, 2, 3, 4, 7, 8, 9 }, { 0, 3, 4, 5, 6, 7, 8, 9 } };
    for (int i = 0; i < 7; i++) {
      int j;
      for (j = 0; j < indexes[i].length; j++) {
        if (number == indexes[i][j])
          break;
      }
      if (j == indexes[i].length)
        continue;
      g.fillRect(segments[i].x, segments[i].y, segments[i].width, segments[i].height);
    }
  }

  class Transition extends Thread {
    public void run() {
      while (!transitionCompleted) {
        int duration = 500;
        int refreshRate = 10;
        switch (state) {
          case 0:
            blind = Math.max(blind - (double) refreshRate / duration, 0);
            if (blind == 0) {
              transitionCompleted = true;
            }
            break;
          case 1:
            blind = Math.min(blind + (double) refreshRate / duration, 1.2);
            if (blind == 1.2) {
              state = 2;
            }
            break;
          case 2:
            blind = Math.max(blind - (double) refreshRate / duration, 0);
            if (blind == 0) {
              transitionCompleted = true;
            }
            break;
          case 3:
            blind = Math.min(blind + (double) refreshRate / duration, 1);
            if (blind == 1) {
              state = 0;
            }
            break;
        }
        repaint();
        try {
          sleep(refreshRate);
        } catch (InterruptedException e) {
        }
      }
    }
  }
}

class Vector2D {
  public double x, y;

  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vector2D add(Vector2D v) {
    this.x += v.x;
    this.y += v.y;
    return this;
  }

  public Vector2D multiplyScalar(double s) {
    this.x *= s;
    this.y *= s;
    return this;
  }

  public Vector2D negate() {
    this.x *= -1;
    this.y *= -1;
    return this;
  }

  public Vector2D clone() {
    return new Vector2D(x, y);
  }
}
