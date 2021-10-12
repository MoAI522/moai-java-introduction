package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataManager {
  private int[] data;

  public boolean loadData(String path) {
    try {
      File file = new File(path);
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      ArrayList<Integer> list = new ArrayList<>();
      String str = br.readLine();
      while (str != null) {
        String[] splittedData = str.split(" +");

        for (String v : splittedData) {
          list.add(Integer.parseInt(v));
        }

        str = br.readLine();
      }
      br.close();
      data = new int[list.size()];
      for (int i = 0; i < list.size(); i++) {
        data[i] = list.get(i);
      }
      return true;
    } catch (FileNotFoundException e) {
      System.err.println("Could not open the file " + path);
    } catch (IOException e) {
      System.err.println("Could not read the file " + path);
    }
    return false;
  }

  public int[] getData() {
    return data;
  }
}
