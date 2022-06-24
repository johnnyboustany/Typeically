package edu.brown.cs.student.main.leaderBoard;

// imports
import java.util.List;
import java.util.Map;

/**
 * Helper class that stores all information that is loaded from
 * the database (loaded from the REPL).
 */
public class StoredDatabase {

  private List<String> tableNames;
  private Map<String, List<String>> completeColumnNames;
  private Map<String, List<Map<String, String>>> completeData;

  /**
   * Initializes the three instance variables it has.
   */
  public StoredDatabase(){
    tableNames = null;
    completeColumnNames = null;
    completeData = null;
  }

  /**
   * Setter for the table names.
   * @param names List
   */
  public void setTableNames(List<String> names){
    tableNames = names;
  }

  /**
   * Setter for the complete column names.
   * @param names Map
   */
  public void setColumnNames(Map<String, List<String>> names){
    completeColumnNames = names;
  }

  /**
   * Setter for the complete table data.
   * @param data Map
   */
  public void setTableData(Map<String, List<Map<String, String>>> data){
    completeData = data;
  }

  /**
   * Getter for the table names.
   * @return List tableNames
   */
  public List<String> getTableNames(){
    return tableNames;
  }

  /**
   * Getter for the column names of a specific table.
   * @return List column names
   */
  public List<String> getColumnNames(String tableName){
    // returns null if the tableName is not contained
    return completeColumnNames.getOrDefault(tableName, null);
  }

  /**
   * Getter for the table data of a specific table.
   * @return List of rows for a table
   */
  public List<Map<String, String>> getTableData(String tableName){
    // returns null if the tableName is not contained
    return completeData.getOrDefault(tableName, null);
  }
}
