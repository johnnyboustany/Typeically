package edu.brown.cs.student.main.leaderBoard;

// imports
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import edu.brown.cs.student.main.Main;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class that handles the /insert endpoint.
 */
public class InsertHandler implements Route {
  private DatabaseLoader databaseLoader;

  /**
   * It takes in a database loader, which it uses to get the stored database shared
   * between the loader and all the handlers.
   *
   */
  public InsertHandler(DatabaseLoader databaseLoader) {
    this.databaseLoader =databaseLoader;
  }

  /**
   * Inherited method, returns Json object depending on
   * the information in the url and state of the database connection.
   *
   * @param request req
   * @param response res
   * @return Json Object
   */
  @Override
  public Object handle(Request request, Response response) {
    Gson GSON = new Gson();

    if (databaseLoader.getConn() == null) {
      List<String> args = new ArrayList<>();
      args.add("load_database");
      args.add("data/leadboards.sqlite3");
      databaseLoader.execute(args);
    }

    if (databaseLoader.getConn() != null) {

      JSONObject requestJSON = null;
      try {
        requestJSON = new JSONObject(request.body());
        System.out.println(requestJSON);
      } catch (JSONException e) {
        System.out.println("ERROR: Invalid parameters passed in.");
      }
      String tableName = "";

      try {
        tableName = requestJSON.getString("tableName");
      } catch (JSONException e) {
        System.out.println("ERROR: Invalid parameters passed in.");
      }

      List<String> tableNames = databaseLoader.getStoredDatabase().getTableNames();

      if (!tableNames.contains(tableName)){
        try {
          this.createTable(tableName);
        } catch (SQLException e) {
          System.out.println("ERROR: Table not created.");
        }
      }

      Map result;
      try {
        List<String> columnNames =  databaseLoader.getStoredDatabase().getColumnNames(tableName);
        Map<String, String> rowToInsert = new HashMap<>();

        for (int col = 0; col < columnNames.size(); col++) {
          String colName = columnNames.get(col);
          String value = requestJSON.getString(colName);
          rowToInsert.put(colName,value);
        }
        this.insertRow(tableName, rowToInsert);
        result = this.getTableDataMap(tableName);

      } catch (JSONException | SQLException e) {
        result = ImmutableMap.of("result", "Failed to insert the row into the table, " + tableName + ".");
      }
      return GSON.toJson(result);

    } else {
      Map error = ImmutableMap.of("error", "Database is not properly loaded.");
      return GSON.toJson(error);
    }
  }

  /**
   * It uses a SQL command to insert a row into the database and also the stored information.
   * @param tableName string
   * @param rowToInsert map (row to insert)
   * @throws JSONException json error
   * @throws SQLException sql error
   */
  public void insertRow(String tableName, Map<String, String> rowToInsert) throws SQLException {
    List<String> columnNames = databaseLoader.getStoredDatabase().getColumnNames(tableName);
    StringBuilder placeholders = new StringBuilder("(");
    if (columnNames.size() == 1) {
      placeholders = new StringBuilder("?");
    } else {
      for (int i = 0; i < columnNames.size() - 1; i++) {
        placeholders.append("?, ");
      }
      placeholders.append("?)");
    }

    String sql = "INSERT INTO " + tableName + " VALUES " + placeholders;
    PreparedStatement insertStatement = databaseLoader.getConn().prepareStatement(sql);
    for (int col = 0; col < columnNames.size(); col++) {
      String colName = columnNames.get(col);
      String value = rowToInsert.get(colName);
      insertStatement.setString(col+1, value);
    }
    insertStatement.executeUpdate();
    insertStatement.close();

    // update data in StoredData
    databaseLoader.setTableData();
  }

  public Map getTableDataMap (String tableName){
    List<String> tableColumnNames = databaseLoader.getStoredDatabase().getColumnNames(tableName);
    List<Map<String, String>> tableData = databaseLoader.getStoredDatabase().getTableData(tableName);
    List<List<String>> rowData = new ArrayList<>();

    for (Map<String, String> rowMap : tableData) {
      List<String> rowList = new ArrayList<>();

      for (String tableColumnName : tableColumnNames) {
        rowList.add(rowMap.get(tableColumnName));
      }
      rowData.add(rowList);
    }
    return ImmutableMap.of("tableName", tableName, "columnNames", tableColumnNames, "rowData", rowData);
  }

  public void createTable (String tableName) throws SQLException {
    String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "("
        + "Username TEXT,"
        + "\"Date\" DATE,"
        + "WPM NUMERIC,"
        + "\"Accuracy (%)\" NUMERIC,"
        + "\"Duration (s)\" NUMERIC);";

    PreparedStatement tableStatement = databaseLoader.getConn().prepareStatement(sql);
    tableStatement.executeUpdate();
    tableStatement.close();

    databaseLoader.setTableNames();
    databaseLoader.setTableColumnNames();
  }
}