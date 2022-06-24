package edu.brown.cs.student.main.databaseLoader;

import edu.brown.cs.student.main.leaderBoard.InsertHandler;
import edu.brown.cs.student.main.leaderBoard.StoredDatabase;
import edu.brown.cs.student.main.leaderBoard.DatabaseLoader;
import org.junit.Test;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DatabaseTest {

  @Test
  public void loadDBTest() {
    DatabaseLoader databaseLoader = new DatabaseLoader();
    StoredDatabase storedDatabase = new StoredDatabase();
    databaseLoader.setStoredDatabase(storedDatabase);

    String command = "load_database data/leadboards.sqlite3";
    List<String> args = Arrays.asList(command.split(" "));
    databaseLoader.execute(args);

    List<String> tableNames = databaseLoader.getStoredDatabase().getTableNames();
    assertNotNull(tableNames);
    assertNotNull(databaseLoader.getStoredDatabase().getColumnNames((tableNames.get(0))));
    assertNotNull(databaseLoader.getStoredDatabase().getTableData((tableNames.get(0))));
  }

  @Test
  public void createAndInsertTableTest() throws SQLException {
    DatabaseLoader databaseLoader = new DatabaseLoader();
    StoredDatabase storedDatabase = new StoredDatabase();
    databaseLoader.setStoredDatabase(storedDatabase);

    String command = "load_database data/leadboards.sqlite3";
    List<String> args = Arrays.asList(command.split(" "));
    databaseLoader.execute(args);
    assertNotNull(databaseLoader.getStoredDatabase().getTableNames());
    assertNotNull(databaseLoader.getStoredDatabase().getColumnNames((databaseLoader.getStoredDatabase().getTableNames().get(0))));
    assertNotNull(databaseLoader.getStoredDatabase().getTableData((databaseLoader.getStoredDatabase().getTableNames().get(0))));

    String tableName = "TESTING";
    InsertHandler insertHandler = new InsertHandler(databaseLoader);
    insertHandler.createTable(tableName);

    List<String> columnNames = databaseLoader.getStoredDatabase().getColumnNames(tableName);

    assertEquals(columnNames.size(), 5);
    assertTrue(columnNames.get(0).equals("Username"));
    assertTrue(columnNames.get(1).equals("Date"));
    assertTrue(columnNames.get(2).equals("WPM"));
    assertTrue(columnNames.get(3).equals("Accuracy (%)"));
    assertTrue(columnNames.get(4).equals("Duration (s)"));

    Map<String, String> rowToInsert = new HashMap<>();
    rowToInsert.put("Username", "Johnny");
    rowToInsert.put("Date", "5-8-2022");
    rowToInsert.put("WPM", "56");
    rowToInsert.put("Accuracy (%)", "96.45");
    rowToInsert.put("Duration (s)", "11");
    insertHandler.insertRow(tableName, rowToInsert);

    List<Map<String, String>> tableData1 = databaseLoader.getStoredDatabase().getTableData(tableName);
    assertTrue(!tableData1.isEmpty());
    assertTrue(tableData1.get(tableData1.size()-1).get("Username").equals("Johnny"));
    assertTrue(tableData1.get(tableData1.size()-1).get("Date").equals("5-8-2022"));
    assertTrue(tableData1.get(tableData1.size()-1).get("WPM").equals("56"));
    assertTrue(tableData1.get(tableData1.size()-1).get("Accuracy (%)").equals("96.45"));
    assertTrue(tableData1.get(tableData1.size()-1).get("Duration (s)").equals("11"));
  }
}
