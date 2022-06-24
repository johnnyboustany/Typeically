package edu.brown.cs.student.main.leaderBoard;

// imports
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Helper class that handles the /table endpoint.
 */
public class TableHandler implements Route {
  private DatabaseLoader databaseLoader;

  /**
   * It takes in a database loader, which it uses to get the stored database shared
   * between the loader and all the handlers.
   * @param databaseLoader has the storedDatabase
   */
  public TableHandler(DatabaseLoader databaseLoader){
    this.databaseLoader = databaseLoader;
  }

  /**
   * Inherited method, returns Json object depending on
   * the information in the url and state of the database connection.
   * @param request req
   * @param response res
   * @return Json Object
   */
  @Override
  public Object handle(Request request, Response response) {
    // determining the table name from the url

    String tableName = request.queryParams("tableName");
    Gson GSON = new Gson();

    if (databaseLoader.getConn() != null) {

      List<String> tableColumnNames = databaseLoader.getStoredDatabase().getColumnNames(tableName);
      List<Map<String, String>> tableData = databaseLoader.getStoredDatabase().getTableData(tableName);
      List<List<String>> rowData = new ArrayList<>();

      if (tableColumnNames == null || tableData == null){
        Map error1 = ImmutableMap.of("error", "Table does not exist.");
        return GSON.toJson(error1);

      } else {

        for (Map<String, String> rowMap : tableData) {
          List<String> rowList = new ArrayList<>();

          for (String tableColumnName : tableColumnNames) {
            rowList.add(rowMap.get(tableColumnName));
          }
          rowData.add(rowList);
        }

        // create an immutable map
        Map tableMap = ImmutableMap.of("tableName", tableName, "columnNames", tableColumnNames, "rowData", rowData);
        return GSON.toJson(tableMap);
      }

    } else {
      Map error2 = ImmutableMap.of("error", "Database is not properly loaded.");
      return GSON.toJson(error2);
    }
  }
}
