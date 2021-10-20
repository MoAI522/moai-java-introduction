package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataManager {
  public enum DataType {
    NUMBER, STRING
  }

  private String[][] data;
  private double[][] numberData;
  private DataType[] types;

  public boolean loadData(String path) {
    File file = new File(path);
    return loadData(file);
  }

  public boolean loadData(File file) {
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      ArrayList<String[]> list = new ArrayList<>();
      ArrayList<double[]> numberList = new ArrayList<>();
      ArrayList<DataType> typeList = new ArrayList<>();
      String str = br.readLine();
      while (str != null) {
        String[] splittedData = str.split(" +");
        list.add(splittedData);

        DataType type = DataType.NUMBER;
        double[] numbers = new double[splittedData.length];
        for (int i = 0; i < splittedData.length; i++) {
          if (splittedData[i].matches("[+-]?\\d*(\\.\\d*)?")) {
            numbers[i] = Double.parseDouble(splittedData[i]);
          } else {
            numbers[i] = 0;
            type = DataType.STRING;
          }
        }
        numberList.add(numbers);
        typeList.add(type);

        str = br.readLine();
      }
      br.close();
      data = list.toArray(new String[list.size()][]);
      numberData = numberList.toArray(new double[numberList.size()][]);
      types = typeList.toArray(new DataType[typeList.size()]);
      return true;
    } catch (FileNotFoundException e) {
      System.err.println("Could not open the file " + file.getAbsolutePath());
    } catch (IOException e) {
      System.err.println("Could not read the file " + file.getAbsolutePath());
    }
    return false;
  }

  public String[][] getData() {
    return data;
  }

  public double[][] getNumberData() {
    return numberData;
  }

  public DataType[] getTypes() {
    return types;
  }
}
