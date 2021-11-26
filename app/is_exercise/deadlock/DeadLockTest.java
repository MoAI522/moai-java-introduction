public class DeadLockTest {
  int fork = 0;
  int knife = 0;

  synchronized void getFork(int n) {
    if (fork != 0) {
      return;
    }
    try {
      while (knife != n && knife != 0) {
        wait();
      }
    } catch (InterruptedException e) {
    }
    fork = n;
    System.out.println(n + " get Fork");
    notify();
  }

  synchronized void getKnife(int n) {
    if (knife != 0) {
      return;
    }
    try {
      while (fork != n && fork != 0) {
        wait();
      }
    } catch (InterruptedException e) {
    }
    knife = n;
    System.out.println(n + " get Knife");
    notify();
  }

  synchronized void reset(int n) {
    if (fork == n)
      fork = 0;
    if (knife == n)
      knife = 0;
    notify();
  }

  synchronized boolean canEat(int n) {
    return ((fork == n) && (knife == n));
  }

  public static void main(String args[]) {
    DeadLockTest dlt = new DeadLockTest();
    for (int i = 1; i < 9; i++) {
      new HumanThread(dlt, i).start();
    }
  }
}

class HumanThread extends Thread {
  DeadLockTest dlt;
  int id;

  public HumanThread(DeadLockTest d, int n) {
    id = n;
    dlt = d;
  }

  public void run() {
    for (int i = 0; i < 10; i++) {
      while (!dlt.canEat(id)) {
        dlt.getFork(id);
        dlt.getKnife(id);
      }
      System.out.println(id + " ate a meal.");
      dlt.reset(id);
    }
  }
}