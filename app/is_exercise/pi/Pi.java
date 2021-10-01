public class Pi {
  private static int precision = 1000;

  public static void main(String[] args) {
    double pi = 0d;
    for (int i = 0; i < precision; i++) {
      pi += (i % 2 == 1 ? -1d : 1d) / (2d * i + 1d);
    }
    pi *= 4;
    System.out.println("PI = " + pi + " (precision: " + precision + ")");
  }
}
