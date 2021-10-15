package util;

public class HSVColor {
  private int[] rgb;

  public HSVColor(double h, double s, double v) {
    rgb = new int[3];

    h = h % 360;
    double max = v;
    double min = max - ((s / 255.0) * max);
    switch ((int) Math.floor(h / 60)) {
      case 0:
        rgb[0] = (int) Math.floor(max);
        rgb[1] = (int) Math.floor((h / 60.0) * (max - min) + min);
        rgb[2] = (int) Math.floor(min);
        break;
      case 1:
        rgb[0] = (int) Math.floor(((120 - h) / 60.0) * (max - min) + min);
        rgb[1] = (int) Math.floor(max);
        rgb[2] = (int) Math.floor(min);
        break;
      case 2:
        rgb[0] = (int) Math.floor(min);
        rgb[1] = (int) Math.floor(max);
        rgb[2] = (int) Math.floor(((h - 120) / 60.0) * (max - min) + min);
        break;
      case 3:
        rgb[0] = (int) Math.floor(min);
        rgb[1] = (int) Math.floor(((240 - h) / 60.0) * (max - min) + min);
        rgb[2] = (int) Math.floor(max);
        break;
      case 4:
        rgb[0] = (int) Math.floor(((h - 240) / 60.0) * (max - min) + min);
        rgb[1] = (int) Math.floor(min);
        rgb[2] = (int) Math.floor(max);
        break;
      case 5:
        rgb[0] = (int) Math.floor(max);
        rgb[1] = (int) Math.floor(min);
        rgb[2] = (int) Math.floor(((360 - h) / 60.0) * (max - min) + min);
        break;
    }
  }

  public int[] getRGB() {
    return rgb;
  }
}
