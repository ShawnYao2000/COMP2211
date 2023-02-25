import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class csvClickLogReader {
  public static void main(String[] args) {
    String filePath = "/Users/mac/Desktop/SEGP/2_week_campaign_2/click_log.csv";
    BufferedReader bufferedReader = null;
    String line = "";
    String delimiter = ",";

    try {
      bufferedReader = new BufferedReader(new FileReader(filePath));
      while ((line = bufferedReader.readLine()) != null) {
        String[] row = line.split(delimiter);
        for (String field : row) {
          System.out.print(field);
        }
        System.out.println();
      }
    } catch (FileNotFoundException e) {
      System.err.println("File not found: " + e.getMessage());
    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException e) {
          System.err.println("Error closing file: " + e.getMessage());
        }
      }
    }
  }
}
