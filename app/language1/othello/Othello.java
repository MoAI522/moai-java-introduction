import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Othello extends JPanel {
  static final int EMPTY = 0, WHITE = 1, BLACK = 2;
  static final int YMAX = 8, XMAX = 8;
  static final int CELL_WIDTH = 32, MARGIN_LEFT = 22, MARGIN_TOP = 30;
  ArrayList<Figure> figs = new ArrayList<Figure>();
  boolean turn = true;
  int winner = EMPTY;
  int[][] board = new int[YMAX][XMAX];
  Text t1 = new Text(20, 25, "オセロ、次の手番: 黒", new Font("Serif", Font.BOLD, 22));
  AvailablePositionResult[] currentAvailablePositions;

  public Othello() {
    figs.add(t1);
    for (int i = 0; i < YMAX * XMAX; ++i) {
      int r = i / YMAX, c = i % YMAX;
      figs.add(new Rect(new Color(11, 118, 25), new Color(0, 0, 0), MARGIN_LEFT + r * CELL_WIDTH,
          MARGIN_TOP + c * CELL_WIDTH,
          CELL_WIDTH, CELL_WIDTH, 1));
    }
    setOpaque(false);
    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        Rect r = pick(evt.getX(), evt.getY());
        if (r == null || winner != EMPTY) {
          return;
        }
        int x = (r.getX() - MARGIN_LEFT) / CELL_WIDTH, y = (r.getY() - MARGIN_TOP) / CELL_WIDTH;
        Position[] effects = new Position[0];
        for (int i = 0; i <= currentAvailablePositions.length; i++) {
          if (i == currentAvailablePositions.length)
            return;
          if (currentAvailablePositions[i].i == y && currentAvailablePositions[i].j == x) {
            effects = currentAvailablePositions[i].effects;
            break;
          }
        }
        if (turn) {
          putStone(y, x, BLACK);
        } else {
          putStone(y, x, WHITE);
        }
        for (int i = 0; i < effects.length; i++) {
          putStone(effects[i].i, effects[i].j, turn ? BLACK : WHITE);
        }
        turn = !turn;
        currentAvailablePositions = availablePosition();
        if (currentAvailablePositions.length == 0) {
          turn = !turn;
          currentAvailablePositions = availablePosition();
          if (currentAvailablePositions.length == 0) {
            int black = 0, white = 0;
            for (int i = 0; i < XMAX; i++) {
              for (int j = 0; j < YMAX; j++) {
                if (board[i][j] == BLACK)
                  black++;
                else if (board[i][j] == WHITE)
                  white++;
              }
            }
            if (black > white) {
              winner = BLACK;
            } else if (black < white) {
              winner = WHITE;
            } else {
              winner = EMPTY;
            }
            t1.setText("終局、" + (winner != EMPTY ? (winner == BLACK ? "黒" : "白") + "の勝利!" : "まさかの引き分け!"));
          } else {
            t1.setText("パス、次の手番: " + (turn ? "黒" : "白"));
          }
        } else {
          t1.setText("次の手番: " + (turn ? "黒" : "白"));
        }
        repaint();
      }
    });

    putStone(3, 3, WHITE);
    putStone(3, 4, BLACK);
    putStone(4, 3, BLACK);
    putStone(4, 4, WHITE);
    currentAvailablePositions = availablePosition();
  }

  public Rect pick(int x, int y) {
    Rect r = null;
    for (Figure f : figs) {
      if (f instanceof Rect && ((Rect) f).hit(x, y)) {
        r = (Rect) f;
      }
    }
    return r;
  }

  void putStone(int i, int j, int which) {
    figs.add(new Stone(MARGIN_LEFT + j * CELL_WIDTH, MARGIN_TOP + i * CELL_WIDTH, CELL_WIDTH - 4, which == WHITE));
    board[i][j] = which;
  }

  public void paintComponent(Graphics g) {
    for (Figure f : figs) {
      f.draw(g);
    }
  }

  class AvailablePositionResult extends Position {
    public Position[] effects;

    public AvailablePositionResult(Position position, Position[] effects) {
      super(position.i, position.j);
      this.effects = effects;
    }
  }

  AvailablePositionResult[] availablePosition() {
    int me = turn ? BLACK : WHITE;
    int enemy = turn ? WHITE : BLACK;
    ArrayList<AvailablePositionResult> list = new ArrayList<AvailablePositionResult>();
    for (int i = 0; i < YMAX; i++) {
      for (int j = 0; j < XMAX; j++) {
        if (board[i][j] != EMPTY)
          continue;
        ArrayList<Position> effects = new ArrayList<Position>();
        Position[] toCheck = { new Position(i - 1, j - 1), new Position(i - 1, j), new Position(i - 1, j + 1),
            new Position(i, j - 1), new Position(i, j + 1), new Position(i + 1, j - 1), new Position(i + 1, j),
            new Position(i + 1, j + 1) };
        for (int k = 0; k < toCheck.length; k++) {
          Position target = toCheck[k];
          ArrayList<Position> tempEffects = new ArrayList<Position>();
          if (!target.isValid() || board[target.i][target.j] != enemy)
            continue;
          tempEffects.add(target);
          int di = target.i - i, dj = target.j - j;
          target = new Position(target.i + di, target.j + dj);
          while (target.isValid()) {
            if (board[target.i][target.j] == me) {
              effects.addAll(tempEffects);
              break;
            } else if (board[target.i][target.j] == EMPTY) {
              break;
            }
            tempEffects.add(target);
            target = new Position(target.i + di, target.j + dj);
          }
        }

        if (effects.size() == 0)
          continue;
        list.add(new AvailablePositionResult(new Position(i, j), effects.toArray(new Position[effects.size()])));
      }
    }

    return list.toArray(new AvailablePositionResult[list.size()]);
  }

  public static void main(String[] args) {
    JFrame app = new JFrame();
    app.add(new Othello());
    app.setSize(300, 300);
    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    app.setVisible(true);
  }

  interface Figure {
    public void draw(Graphics g);
  }

  static class Text implements Figure {
    int xpos, ypos;
    String txt;
    Font fn;

    public Text(int x, int y, String t, Font f) {
      xpos = x;
      ypos = y;
      txt = t;
      fn = f;
    }

    public void setText(String t) {
      txt = t;
    }

    public void draw(Graphics g) {
      g.setColor(Color.BLACK);
      g.setFont(fn);
      g.drawString(txt, xpos, ypos);
    }
  }

  static class Stone implements Figure {
    int xpos, ypos, size;
    boolean white;

    public Stone(int x, int y, int s, boolean white) {
      xpos = x;
      ypos = y;
      size = s;
      this.white = white;
    }

    public void draw(Graphics g) {
      g.setColor(white ? Color.WHITE : Color.BLACK);
      g.fillOval(xpos + 2, ypos + 2, size, size);
    }
  }

  static class Rect implements Figure {
    Color col1, col2;
    int xpos, ypos, width, height, borderWidth;

    public Rect(Color c1, Color c2, int x, int y, int w, int h, int borderWidth) {
      col1 = c1;
      col2 = c2;
      xpos = x;
      ypos = y;
      width = w;
      height = h;
      this.borderWidth = borderWidth;
    }

    public boolean hit(int x, int y) {
      return xpos <= x && x <= xpos + width && ypos <= y && y <= ypos + height;
    }

    public int getX() {
      return xpos;
    }

    public int getY() {
      return ypos;
    }

    public void draw(Graphics g) {
      g.setColor(col2);
      g.fillRect(xpos, ypos, width, height);
      g.setColor(col1);
      g.fillRect(xpos + borderWidth, ypos + borderWidth, width - borderWidth * 2,
          height - borderWidth * 2);
    }
  }

  class Position {
    public int j, i;

    public Position(int i, int j) {
      this.j = j;
      this.i = i;
    }

    public boolean isValid() {
      return j >= 0 && j < XMAX && i >= 0 && i < YMAX;
    }
  }
}