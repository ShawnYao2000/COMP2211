import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class csvClickLogReader {
  public static void main(String[] args) {
    String filePath = "/Users/mac/Desktop/SEGP/2_week_campaign_2/click_log.csv";
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
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS click_log (date TEXT, id TEXT, click_cost REAL)");

      // Prepare the insert statement
      String insertSql = "INSERT INTO click_log (date, id, click_cost) VALUES (?, ?, ?)";
      preparedStatement = connection.prepareStatement(insertSql);

      bufferedReader = new BufferedReader(new FileReader(filePath));
      bufferedReader.readLine();
      while ((line = bufferedReader.readLine()) != null) {
        String[] row = line.split(delimiter);

        // Set the values for the prepared statement
        preparedStatement.setString(1, row[0]);
        preparedStatement.setString(2, row[1]);
        preparedStatement.setDouble(3, Double.parseDouble(row[2]));

        // Execute the prepared statement
        preparedStatement.executeUpdate();
      }

      // Select the data from the click_log table
      String selectSql = "SELECT * FROM click_log";
      Statement selectStatement = connection.createStatement();
      ResultSet resultSet = selectStatement.executeQuery(selectSql);

      // Print out the data for verification
      System.out.println("Inserted data:");
      System.out.println("---------------");
      while (resultSet.next()) {
        String date = resultSet.getString("date");
        String id = resultSet.getString("id");
        double clickCost = resultSet.getDouble("click_cost");
        System.out.println(date + ", " + id + ", " + clickCost);
      }

      // Close the result set, select statement, and file reader
      resultSet.close();
      selectStatement.close();

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
