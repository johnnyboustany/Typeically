package edu.brown.cs.student.main.leaderBoard;

// imports
import edu.brown.cs.student.main.REPL.REPLable;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class VerifyAPI implements REPLable {
  // private variable declarations

  /**
   * Inherited method, prints description of class
   * @return [description of class]
   */
  @Override
  public String getDescription() {
    return "Helper class that runs a demo to see if api works.";
  }

  /**
   * Inherited method, connects to the database
   * @param args [command and file name]
   */
  @Override
  public void execute(List<String> args) {
    // checks for correct number of args

    if (args.get(1).equals("database")){
      this.databaseEndpoint();
    } else if(args.size() == 3 && args.get(1).equals("table")){
      this.tableEndpoint(args.get(2));
    } else if(args.size() == 7 && args.get(1).equals("insert")){
      this.insertEndpoint(args.get(2), args.get(3), args.get(4), args.get(5), args.get(6));
    } else if(args.size() == 7 && args.get(1).equals("update")){
      this.updateEndpoint(args.get(2), args.get(3), args.get(4), args.get(5), args.get(6));
    } else if(args.size() == 5 && args.get(1).equals("delete")){
      this.deleteEndpoint(args.get(2), args.get(3), args.get(4));
    } else {
      System.out.println("ERROR: invalid command.");
    }
  }

  /**
   * Makes a get request to the given server.
   * @param reqUri url
   * @return HttpResponse<String> apiResponse
   */
  public HttpResponse<String> makeGetReq(String reqUri) {
    HttpResponse<String> apiResponse = null;
    HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(60)).build();
    HttpRequest request = HttpRequest.newBuilder(URI.create(reqUri)).GET()
        .timeout(Duration.ofMinutes(2)).build();
    try {
      apiResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException |InterruptedException e) {
      System.out.println("failed to send request");
    }
    return apiResponse;
  }

  /**
   * Makes a post request to the given server.
   * @param reqUri url
   * @return HttpResponse<String> apiResponse
   */
  public HttpResponse<String> makePostReq(String reqUri, String body) {
    HttpResponse<String> apiResponse = null;
    HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(60)).build();
    HttpRequest request = HttpRequest.newBuilder(URI.create(reqUri)). POST(HttpRequest.BodyPublishers.ofString(body))
        .timeout(Duration.ofMinutes(2)).build();
    try {
      apiResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException |InterruptedException e) {
      System.out.println("failed to send request");
    }
    return apiResponse;
  }

  /**
   * Makes a request to the /database endpoint
   * and prints out result.
   */
  public void databaseEndpoint() {
    String reqUri = "http://localhost:4567/database";
    HttpResponse<String> apiResponse = this.makeGetReq(reqUri);
    if (apiResponse != null) {
      System.out.println(apiResponse.statusCode());
      System.out.println(apiResponse.body());
    } else {
      System.out.println("ERROR: Server is not running.");
    }
  }

  /**
   * Makes a request to the /table endpoint
   * and prints out result.
   */
  public void tableEndpoint(String tableName) {
    String reqUri = "http://localhost:4567/table?tableName=" + tableName;
    HttpResponse<String> apiResponse = this.makeGetReq(reqUri);
    if (apiResponse != null) {
      System.out.println(apiResponse.statusCode());
      System.out.println(apiResponse.body());
    } else {
      System.out.println("ERROR: Server is not running.");
    }
  }

  /**
   * Makes a request to the /update endpoint
   * and prints out result.
   * @param columnToIdentify string
   * @param columnToSet string
   * @param valueToIdentify string
   * @param valueToSet string
   * @param tableName string
   */
  public void updateEndpoint(String tableName,  String columnToSet, String valueToSet, String columnToIdentify, String valueToIdentify) {
    String updateValue =  "{\"tableName\":\"" + tableName + "\","
        + " \"columnToSet\":\"" + columnToSet + "\"," + " \"valueToSet\":\"" + valueToSet + "\"," + " \"columnToIdentify\":\"" + columnToIdentify + "\"," +
        " \"valueToIdentify\":\"" + valueToIdentify + "\"}";

    String reqUri = "http://localhost:4567/update";
    HttpResponse<String> apiResponse = this.makePostReq(reqUri, updateValue);

    if (apiResponse != null) {
      System.out.println(apiResponse.statusCode());
      System.out.println(apiResponse.body());
    } else {
      System.out.println("ERROR: Server is not running.");

    }
  }

  /**
   * Makes a request to the /delete endpoint
   * and prints out result.
   * @param tableName string
   * @param columnName string
   * @param value string
   * @param tableName string
   */
  public void deleteEndpoint(String tableName, String columnName, String value){
    String deleteValue =  "{\"tableName\":\"" + tableName + "\","
        + " \"columnName\":\"" + columnName + "\"," + " \"value\":\"" + value + "\"}";

    String reqUri = "http://localhost:4567/delete";
    HttpResponse<String> apiResponse = this.makePostReq(reqUri, deleteValue);
    if (apiResponse != null) {
      System.out.println(apiResponse.statusCode());
      System.out.println(apiResponse.body());
    } else {
      System.out.println("ERROR: Server is not running.");
    }
  }

  /**
   * Makes a request to the /insert endpoint
   * and prints out result.
   * @param tableName string
   * @param value1 string
   * @param value2 string
   */
  public void insertEndpoint(String tableName, String value1, String value2, String value3, String value4) {
    String insertValue =  "{\"tableName\":\"" + tableName + "\","
        + " \"Username\":\"" + value1 + "\"," + " \"WPM\":\"" + value2 + "\","
        + " \"Accuracy (%)\":\"" + value3 + "\"," + " \"Duration (s)\":\"" + value4 + "\"}";

    String reqUri = "http://localhost:4567/insert";
    HttpResponse<String> apiResponse = this.makePostReq(reqUri, insertValue);
    if (apiResponse != null) {
      System.out.println(apiResponse.statusCode());
      System.out.println(apiResponse.body());
    } else {
      System.out.println("ERROR: Server is not running.");
    }
  }
}
