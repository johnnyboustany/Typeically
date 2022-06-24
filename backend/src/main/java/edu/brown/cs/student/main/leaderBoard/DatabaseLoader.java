package edu.brown.cs.student.main.leaderBoard;

// imports
import edu.brown.cs.student.main.leaderBoard.StoredDatabase;
import edu.brown.cs.student.main.REPL.REPLable;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class that loads a sqlite3 table in the backend
 */
public class DatabaseLoader implements REPLable {
  // private variable declarations
  private static Connection conn = null;
  private StoredDatabase storedDatabase;

  public DatabaseLoader(){
    storedDatabase = null;
  }
  /**
   * Inherited method, prints description of class
   * @return [description of class]
   */
  @Override
  public String getDescription() {
    return "Helper class that loads a table in the backend, to be displayed on a webpage.";
  }

  /**
   * Inherited method, connects to the database
   * @param args [command and file name]
   */
  @Override
  public void execute(List<String> args) {
    // checks for correct number of args
    if (args.size() == 2) {
      String filename = args.get(1);
      // checks that the file exists
      if (this.checkValidFile(filename)) {
        try {
          // initializes the connection
          this.initializeConnection(filename);
          this.setTableNames();
          this.setTableColumnNames();
          this.setTableData();

        } catch (ClassNotFoundException e) {
          System.out.println("ERROR: class not found exception");
        } catch (SQLException e) {
          System.out.println("ERROR: sql exception");
        }
      } else {
        System.out.println("ERROR: invalid file path");
      }
    } else {
      System.out.println("ERROR: incorrect number of arguments.");
    }
  }

  /**
   * Helper method that checks if a file exists
   * @param filename [file to be checked]
   * @return [true or false depending on if file exists]
   */
  public boolean checkValidFile(String filename) {
    File f = new File(filename);
    if (!f.exists()) {
      return false;
    }
    return true;
  }

  /**
   * Method that initializes connection to database
   * @param filename [name of file containing data]
   * @throws ClassNotFoundException [exception]
   * @throws SQLException [exception]
   */
  public void initializeConnection(String filename) throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + filename;
    conn = DriverManager.getConnection(urlToDB);

    System.out.println("Connection to " + filename + " initialized.");
  }

  /**
   * Helper method to set the list of tables names
   * from the loaded database.
   * author: Johnny
   *
   */
  public void setTableNames() {
    List<String> tableNames = new ArrayList<>();
    String sql = "SELECT name FROM sqlite_master WHERE type='table'";

    try {
      PreparedStatement tableStatement = conn.prepareStatement(sql);
      ResultSet tableResultSet = tableStatement.executeQuery();

      // next moves the cursor froward one row from its current position
      while (tableResultSet.next()) {
        // all the table names are in column 1
        tableNames.add(tableResultSet.getString(1));
      }
      tableResultSet.close();
      tableStatement.close();
    } catch (SQLException e) {
      System.out.println("ERROR: Invalid sql command");
    }
    storedDatabase.setTableNames(tableNames);
  }


  /**
   * Helper method to set the list of column names
   * from a specific table.
   * author: Johnny
   *
   */
  public void setTableColumnNames() {
    Map<String, List<String>> completeColumnNames = new HashMap<>();

    for (String name: storedDatabase.getTableNames()) {
      List<String> columnNames = new ArrayList<>();

      String sql = "SELECT name FROM PRAGMA_TABLE_INFO('" + name + "')";

      try {
        PreparedStatement colStatement = conn.prepareStatement(sql);
        ResultSet colResultSet = colStatement.executeQuery();

        // next moves the cursor froward one row from its current position
        while (colResultSet.next()) {
          // all the column names are in column 1

          columnNames.add(colResultSet.getString(1));
        }
        completeColumnNames.put(name, columnNames);
        colResultSet.close();
        colStatement.close();
      } catch (SQLException e) {
      System.out.println("ERROR: Invalid sql command");
      }
    }
    storedDatabase.setColumnNames(completeColumnNames);
  }

  /**
   * Helper method to get the list of maps corresponding to each row
   * for a specific table. This is the table data.
   * author: Johnny
   *
   */
  public void setTableData() {
    Map<String, List<Map<String, String>>> completeTableData = new HashMap<>();

    for (String name: storedDatabase.getTableNames()) {

      List<Map<String, String>> tableData = new ArrayList<>();
      List<String> columnNames = storedDatabase.getColumnNames(name);

      String sql = "SELECT * FROM " + name + " ORDER BY \"Duration (s)\" ASC LIMIT 5;";

      try {
        PreparedStatement dataStatement = conn.prepareStatement(sql);
        ResultSet dataResultSet = dataStatement.executeQuery();

        // looping through every row
        while (dataResultSet.next()) {
          Map<String, String> rowMap = new HashMap<>();
          // first string is column name and second string is the value at a corresponding row

          // looping through every column to fill out rowMap
          for (int col = 0; col < columnNames.size(); col++) {
            rowMap.put(columnNames.get(col), dataResultSet.getString(col+1));
          }
          tableData.add(rowMap);
        }
        completeTableData.put(name, tableData);
        dataResultSet.close();
        dataStatement.close();
      } catch (SQLException e) {
        System.out.println("ERROR: Invalid sql command");
      }
    }
    storedDatabase.setTableData(completeTableData);
  }

  /**
   * Getter method that returns the connection
   * @return [connection]
   */
  public Connection getConn() {
    return conn;
  }

  /**
   * Getter method that initializes the storedDatabase instance variable.
   *
   */
  public void setStoredDatabase(StoredDatabase storedDatabase){
    this.storedDatabase = storedDatabase;
  }

  /**
   * Getter method that returns what is stored in the storedDatabase instance variable.
   * @return StoredDatabase data
   */
  public StoredDatabase getStoredDatabase(){
   return storedDatabase;
  }
}