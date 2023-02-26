import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class csvServerLogReader {
  public static void main(String[] args) {
    String csvFilePath = "/Users/mac/Desktop/SEGP/2_month_campaign/server_log.csv";
    try {
      List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
      for (String line : lines) {
        String[] serverLog = line.split(",");
        for (String entry : serverLog){
          System.out.print(entry);
        }
        System.out.println("");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}



