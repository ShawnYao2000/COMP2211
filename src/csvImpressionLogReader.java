import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class csvImpressionLogReader {
  public static void main(String[] args) {
    String filePath = "/Users/mac/Desktop/SEGP/2_week_campaign_2/impression_log.csv";
    BufferedReader bufferedReader = null;
    String line = "";
    String delimiter = ",";

    // Database connection details
    String jdbcUrl = "jdbc:sqlite:/Users/mac/IdeaProjects/1206/COMP2211/logDatabase.db";

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      // Connect to the database
      connection = DriverManager.getConnection(jdbcUrl);

      // Create the click_log table if it doesn't exist
      Statement statement = connection.createStatement();
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS impression_log (date TEXT, id TEXT, gender TEXT, age TEXT, income TEXT, context TEXT, impression_cost REAL)");

      // Prepare the insert statement
      String insertSql = "INSERT INTO impression_log (date, id, gender, age, income, context, impression_cost) VALUES (?, ?, ?, ?, ?, ?, ?)";
      preparedStatement = connection.prepareStatement(insertSql);

      bufferedReader = new BufferedReader(new FileReader(filePath));
      bufferedReader.readLine();
      while ((line = bufferedReader.readLine()) != null) {
        String[] row = line.split(delimiter);

        // Set the values for the prepared statement
        preparedStatement.setString(1, row[0]);
        preparedStatement.setString(2, row[1]);
        preparedStatement.setString(3, row[2]);
        preparedStatement.setString(4, row[3]);
        preparedStatement.setString(5, row[4]);
        preparedStatement.setString(6, row[5]);
        preparedStatement.setDouble(7, Double.parseDouble(row[6]));

        // Execute the prepared statement
        preparedStatement.executeUpdate();
      }

    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
    } catch (SQLException e) {
      System.err.println("Database error: " + e.getMessage());
    } finally {
      // Close the database connection and prepared statement
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        System.err.println("Error closing connection: " + e.getMessage());
      }

      // Close the file reader
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
