import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class sqlExecutor {

  public static void main(String[] args) {
    String scriptPath = "/Users/mac/IdeaProjects/1206/COMP2211/bounceCount.sql";
    String jdbcUrl = "jdbc:sqlite:/Users/mac/IdeaProjects/1206/COMP2211/logDatabase.db";

    try {
      // Load the SQLite JDBC driver
      Class.forName("org.sqlite.JDBC");

      // Create a connection to the database
      Connection connection = DriverManager.getConnection(jdbcUrl);

      // Read the SQL script from a file
      String sqlScript = new String(Files.readAllBytes(Paths.get(scriptPath)), StandardCharsets.UTF_8);

      // Split the SQL script into individual queries
      String[] queries = sqlScript.split(";");

      // Execute each query in the script
      for (String query : queries) {
        // Skip over empty or whitespace-only queries
        if (query.trim().isEmpty()) {
          continue;
        }

        // Create a new statement and execute the query
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        // Print out the results of the query
        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();
        while (resultSet.next()) {
          for (int i = 1; i <= columnCount; i++) {
            System.out.print(resultSet.getString(i) + "\t");
          }
          System.out.println();
        }

        // Clean up resources
        resultSet.close();
        statement.close();
      }

      // Close the connection
      connection.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
