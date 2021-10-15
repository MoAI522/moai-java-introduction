package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataManager {
  private String[][] data;

  public boolean loadData(String path) {
    try {
      File file = new File(path);
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      ArrayList<String[]> list = new ArrayList<>();
      String str = br.readLine();
      while (str != null) {
        String[] splittedData = str.split(" +");
        list.add(splittedData);
        str = br.readLine();
      }
      br.close();
      data = list.toArray(new String[list.size()][]);
      return true;
    } catch (FileNotFoundException e) {
      System.err.println("Could not open the file " + path);
    } catch (IOException e) {
      System.err.println("Could not read the file " + path);
    }
    return false;
  }

  public String[][] getData() {
    return data;
  }
}
