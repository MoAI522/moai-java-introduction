public class Reservation {
  int seat[][];

  public Reservation(int n, int m) {
    seat = new int[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        seat[i][j] = -1;
      }
    }
  }

  synchronized boolean reserve(int id, int num) {
    for (int i = 0; i < seat.length; i++) {
      for (int j = 0; j < seat[i].length - num + 1; j++) {
        int k;
        for (k = 0; k < num; k++) {
          if (seat[i][j + k] != -1)
            break;
        }
        if (k == num) {
          for (k = 0; k < num; k++) {
            seat[i][j + k] = id;
          }
          return true;
        }
      }
    }
    return false;
  }

  void printSeat() {
    for (int i = 0; i < seat.length; i++) {
      for (int j = 0; j < seat[i].length; j++) {
        System.out.print((seat[i][j] >> 8) + "" + (char) ('a' + (0x000000ff & seat[i][j])) + " ");
      }
      System.out.println();
    }
  }

  public static void main(String args[]) {
    int thread_num = 5;
    Reservation rs = new Reservation(6, 15);
    Passengers ps[] = new Passengers[thread_num];
    for (int i = 0; i < thread_num; i++) {
      ps[i] = new Passengers(i, rs);
    }
    for (int i = 0; i < thread_num; i++) {
      try {
        ps[i].join();
      } catch (InterruptedException e) {
      }
    }
    rs.printSeat();
  }
}

class Passengers extends Thread {
  int id;
  Reservation rs;

  public Passengers(int n, Reservation rs) {
    this.id = n;
    this.rs = rs;
    this.start();
  }

  public void run() {
    for (int i = 0; i < 10; i++) {
      int num = (int) (Math.random() * 6 + 1);
      if (rs.reserve((int) ((id << 8) + i), num)) {
        System.out.println("ID:" + id + "(" + (char) ('a' + i) + ")" + "  reserved " + num + " seats.");
      } else {
        System.out.println("ID:" + id + "(" + (char) ('a' + i) + ")" + "  failed to reserve " + num + " seats.");
      }
    }
  }
}