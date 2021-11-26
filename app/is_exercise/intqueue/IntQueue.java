public class IntQueue {
  final int SIZE = 10;
  private int[] values = new int[SIZE];
  private int tail = -1;
  private int count = 0;

  synchronized boolean enqueue(int data) {
    if (data < 0) {
      return false;
    }
    try {
      while (tail == SIZE - 1) {
        System.out.println("queue is full. waiting for dequeue()");
        wait();
      }
    } catch (InterruptedException e) {
    }
    tail++;
    values[tail] = data;
    count++;
    System.out.println("[" + count + "]enqueue :" + data + " tail:" + tail);
    notify();
    return true;
  }

  synchronized int dequeue() {
    try {
      while (tail == -1) {
        System.out.println("queue is empty. waiting for enqueue().");
        wait();
      }
    } catch (InterruptedException e) {
    }
    int data = values[0];
    for (int i = 0; i < SIZE - 1; i++) {
      values[i] = values[i + 1];
    }
    tail--;
    count++;
    System.out.println("[" + count + "]dequeue :" + data + " tail:" + tail);
    notify();
    return data;
  }

  public static void main(String[] args) {
    IntQueue q = new IntQueue();
    EnQueueThread eq1 = new EnQueueThread(q);
    EnQueueThread eq2 = new EnQueueThread(q);
    DeQueueThread dq1 = new DeQueueThread(q);
    DeQueueThread dq2 = new DeQueueThread(q);
    eq1.start();
    eq2.start();
    dq1.start();
    dq2.start();
  }
}

class EnQueueThread extends Thread {
  IntQueue q;

  public EnQueueThread(IntQueue q) {
    this.q = q;
  }

  public void run() {
    for (int i = 0; i < 100; i++) {
      int data = (int) (Math.random() * 100 + 1);
      q.enqueue(data);
    }
  }
}

class DeQueueThread extends Thread {
  IntQueue q;

  public DeQueueThread(IntQueue q) {
    this.q = q;
  }

  public void run() {
    for (int i = 0; i < 100; i++) {
      q.dequeue();
    }
  }
}