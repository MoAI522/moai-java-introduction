import java.util.ArrayList;
import java.util.Arrays;

public class Sort {
  private enum Type {
    BUBBLE, QUICK
  }

  private int array[];

  public Sort(int n, Type type) {
    array = new int[n];
    for (int i = 0; i < n; i++) {
      array[i] = (int) (Math.random() * Integer.MAX_VALUE);
    }

    switch (type) {
      case BUBBLE: {
        long start = System.currentTimeMillis();
        BubbleSort bs = new BubbleSort(array);
        array = bs.getArray();
        // printArray(array);
        long end = System.currentTimeMillis();
        System.out.println("bubble sort: " + sortCheck(array) + ", Processing time: " + (end - start) + "ms");
        break;
      }
      case QUICK: {
        long start = System.currentTimeMillis();
        QuickSort qs = new QuickSort(array);
        array = qs.getArray();
        // printArray(array);
        long end = System.currentTimeMillis();
        System.out.println("quick sort: " + sortCheck(array) + ", Processing time: " + (end - start) + "ms");
        break;
      }
    }
  }

  public static boolean sortCheck(int array[]) {
    for (int i = 0; i < array.length - 1; i++) {
      if (array[i] > array[i + 1])
        return false;
    }
    return true;
  }

  public static void printArray(int array[]) {
    for (int i = 0; i < array.length; i++) {
      System.out.print(array[i] + " ");
    }
    System.out.println();
  }

  public static void main(String args[]) {
    new Sort(100000, Type.BUBBLE);
    new Sort(100000, Type.QUICK);
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

class QuickSort extends Thread {
  private int originArray[];

  QuickSort(int[] n) {
    originArray = n;
    this.start();
    try {
      this.join();
    } catch (Exception e) {
    }
  }

  public void run() {
    sort(originArray, originArray.length);
  }

  private void sort(int[] array, int length) {
    if (length <= 1)
      return;

    int pivot = array[0];
    int i;
    for (i = 1; i < length; i++) {
      if (pivot > array[i]) {
        break;
      } else if (pivot < array[i]) {
        pivot = array[i];
        break;
      }
    }

    int splitIndex = 0;
    int hipSearchIndex = length - 1;
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

    if (splitIndex == 0 || length - 1 - hipSearchIndex == 0) {
      return;
    }

    int[] secondaryArray = new int[length - 1 - hipSearchIndex];
    System.arraycopy(array, splitIndex, secondaryArray, 0, secondaryArray.length);

    QuickSort th = new QuickSort(secondaryArray);
    sort(array, splitIndex);
    try {
      th.join();
    } catch (InterruptedException e) {
    }

    System.arraycopy(th.getArray(), 0, array, splitIndex, secondaryArray.length);
  }

  public int[] getArray() {
    return originArray;
  }
}