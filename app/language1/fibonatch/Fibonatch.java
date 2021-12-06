public class Fibonatch {
  static long fib(int n) {
    if (n == 0)
      return 0;
    else if (n == 1)
      return 1;
    else
      return (fib(n - 2) + fib(n - 1));
  }

  static long fib2(int n) {
    if (n == 0)
      return 0;
    else if (n == 1)
      return 1;
    else {
      long p0 = 0, p1 = 1, p2 = p0 + p1;
      for (int i = 2; i < n; i++) {
        p0 = p1;
        p1 = p2;
        p2 = p0 + p1;
      }
      return p2;
    }
  }

  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    for (int i = 0; i <= 50; i++) {
      System.out.println("fib(" + i + ")= " + fib(i));
    }
    long endTime = System.currentTimeMillis();
    System.out.println("[fib]process time: " + (endTime - startTime) + "ms");

    startTime = System.currentTimeMillis();
    for (int i = 0; i <= 50; i++) {
      System.out.println("fib2(" + i + ")= " + fib2(i));
    }
    endTime = System.currentTimeMillis();
    System.out.println("[fib2]process time: " + (endTime - startTime) + "ms");
  }
}
