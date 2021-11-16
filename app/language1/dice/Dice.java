import java.util.concurrent.ThreadLocalRandom;

public class Dice {
  public static void main(String[] args) {
    double p[] = { 0.2, 0.1, 0.2, 0.1, 0.3, 0.1 };
    Dice d = new Dice();
    d.start(p);
  }

  public void start(double[] p) {
    int n = p.length;
    int count[] = new int[n];
    long startTime = System.currentTimeMillis();

    for (int i = 0; i < 100000000; i++) {
      count[dice(p)] += 1;
    }

    long endTime = System.currentTimeMillis();
    System.out.println("処理時間: " + (endTime - startTime) + "msec");

    for (int i = 0; i < n; i++) {
      double rate = (double) count[i] / 100000000;
      double gap = rate - p[i];
      System.out.printf("%d:%10d (%6f, %+6f)\n", i + 1, count[i], rate, gap);
    }
  }

  public int dice(double[] p) {
    double rand = ThreadLocalRandom.current().nextDouble();
    int len = p.length;
    double sum = 0;
    for (int i = 0; i < len; i++) {
      sum += p[i];
      if (rand < sum) {
        return i;
      }
    }

    return len - 1;
  }
}
