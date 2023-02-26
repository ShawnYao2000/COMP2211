import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class csvImpressionLogReader {
  public static void main(String[] args) {
    String csvFilePath = "/Users/mac/Desktop/SEGP/2_month_campaign/impression_log.csv";
    try {
      List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
      for (String line : lines) {
        String[] impression = line.split(",");
        System.out.println(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
