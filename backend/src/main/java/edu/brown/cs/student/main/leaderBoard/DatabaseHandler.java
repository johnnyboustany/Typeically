package edu.brown.cs.student.main.leaderBoard;

// imports
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.List;
import java.util.Map;

/**
 * Helper class that handles the /database endpoint.
 */
public class DatabaseHandler implements Route {
  private DatabaseLoader databaseLoader;

  /**
   * It takes in a database loader, which it uses to get the stored database shared
   * between the loader and all the handlers.
   * @param databaseLoader has the storedDatabase
   */
  public DatabaseHandler(DatabaseLoader databaseLoader){
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
    //System.out.println(tableLoader.getStoredDatabase().getTableNames());
    List<String> tableNames = databaseLoader.getStoredDatabase().getTableNames();
    Gson GSON = new Gson();

    if (tableNames != null) {
      // create an immutable map
      Map tableNamesMap = ImmutableMap.of("tableNames", tableNames);

      // return a json of the data
      return GSON.toJson(tableNamesMap);
    } else {
      Map error = ImmutableMap.of("error", "Database is not properly loaded.");
      return GSON.toJson(error);
    }
  }
}
