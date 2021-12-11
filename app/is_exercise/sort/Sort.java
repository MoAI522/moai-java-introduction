import java.util.ArrayList;
import java.util.Arrays;

public class Sort {
  public Sort(int n) {
    System.out.println("elements: " + n);

    int[] array1 = new int[n];
    for (int i = 0; i < n; i++) {
      array1[i] = (int) (Math.random() * Integer.MAX_VALUE);
    }
    int[] array2 = array1.clone();
    // int[] array2 = array1;

    long start = System.currentTimeMillis();
    BubbleSort bs = new BubbleSort(array1);
    array1 = bs.getArray();
    long end = System.currentTimeMillis();
    System.out.println("bubble sort: " + sortCheck(array1) + ", Processing time:" + (end - start) + "ms");

    start = System.currentTimeMillis();
    QuickSort qs = new QuickSort(array2);
    array2 = qs.getArray();
    end = System.currentTimeMillis();
    System.out.println("quick sort: " + sortCheck(array2) + ", Processing time: "
        + (end - start) + "ms");
  }

  public static boolean sortCheck(int array[]) {
    for (int i = 0; i < array.length - 1; i++) {
      if (array[i] > array[i + 1])
        return false;
    }
    return true;
  }

  public static void main(String args[]) {
    new Sort(100000);
  }
}

class BubbleSort {
  private int array[];

  BubbleSort(int[] n) {
    array = n;
    sort();
  }

  private void sort() {
    for (int i = array.length - 1; i > 0; i--) {
      for (int j = 0; j < i; j++) {
        if (array[j] > array[j + 1]) {
          int tmp = array[j];
          array[j] = array[j + 1];
          array[j + 1] = tmp;
        }
      }
    }
  }

  public int[] getArray() {
    return array;
  }
}

class QuickSort {
  private int[] array;

  QuickSort(int[] n) {
    array = n;
    SortThread st = new SortThread(0, n.length, 0);
    try {
      st.join();
    } catch (Exception e) {
    }
  }

  class SortThread extends Thread {
    private int from, length, depth;

    SortThread(int from, int length, int depth) {
      this.from = from;
      this.length = length;
      this.depth = depth;
      this.start();
    }

    public void run() {
      sort(from, length);
    }

    private void sort(int from, int length) {
      if (length <= 1)
        return;

      int pivot = array[from];
      for (int i = from + 1; i < from + length; i++) {
        if (pivot > array[i]) {
          break;
        } else if (pivot < array[i]) {
          pivot = array[i];
          break;
        }
      }

      int splitIndex = from;
      int hipSearchIndex = from + length - 1;
      for (; splitIndex <= hipSearchIndex; splitIndex++) {
        if (array[splitIndex] >= pivot) {
          boolean replaced = false;
          for (; hipSearchIndex >= splitIndex; hipSearchIndex--) {
            if (array[hipSearchIndex] < pivot) {
              int temp = array[splitIndex];
              array[splitIndex] = array[hipSearchIndex];
              array[hipSearchIndex] = temp;
              hipSearchIndex--;
              replaced = true;
              break;
            }
          }
          if (!replaced)
            break;
        }
      }

      if (splitIndex - from == 0 || (from + length - 1) - hipSearchIndex == 0) {
        return;
      }

      if (depth < 3) {
        SortThread th = new SortThread(splitIndex, from + length - 1 - hipSearchIndex, depth + 1);
        sort(from, splitIndex - from);
        try {
          th.join();
        } catch (InterruptedException e) {
        }
      } else {
        sort(from, splitIndex - from);
        sort(splitIndex, from + length - 1 - hipSearchIndex);
      }
    }
  }

  public int[] getArray() {
    return array;
  }
}