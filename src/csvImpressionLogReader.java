import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class csvImpressionLogReader {
  public static void main(String[] args) {
    String csvFilePath = "/Users/mac/Desktop/SEGP/2_month_campaign/impression_log.csv";
    String jdbcUrl = "jdbc:sqlite:/Users/mac/IdeaProjects/1206/COMP2211/logDatabase.db";

    try {
      // Create a connection to the database
      Connection conn = DriverManager.getConnection(jdbcUrl);

      // Create a new table to store the CSV data
      String createTableSql = "CREATE TABLE IF NOT EXISTS impression_log ("
          + "date TEXT, "
          + "id TEXT, "
          + "gender TEXT, "
          + "age TEXT, "
          + "income TEXT, "
          + "context TEXT, "
          + "impression_cost REAL"
          + ")";
      conn.createStatement().execute(createTableSql);

      // Prepare a SQL statement to insert a row of data into the table
      String insertSql = "INSERT INTO impression_log (date, id, gender, age, income, context, impression_cost) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement insertStmt = conn.prepareStatement(insertSql);

      // Read the lines of the CSV file and insert them into the database
      List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
      for (int i = 1; i < lines.size(); i++) {  // start at index 1 to skip header
        String line = lines.get(i);
        String[] impression = line.split(",");
        insertStmt.setString(1, impression[0]);
        insertStmt.setString(2, impression[1]);
        insertStmt.setString(3, impression[2]);
        insertStmt.setString(4, impression[3]);
        insertStmt.setString(5, impression[4]);
        insertStmt.setString(6, impression[5]);
        insertStmt.setDouble(7, Double.parseDouble(impression[6]));
        insertStmt.executeUpdate();
      }

      // Close the database connection
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
