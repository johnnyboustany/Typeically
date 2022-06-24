
package edu.brown.cs.student.main.spotify;

// imports

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class that handles the /spotify endpoint.
 */

public class SpotifyHandler implements Route {

/**
   * Inherited method, returns Json object.
   * @param request req
   * @param response res
   * @return Json Object
   */

  @Override
  public Object handle(Request request, Response response) {
    TopTracks getTopTracks = new TopTracks();
    List<HashMap<String, String>> topTracks = getTopTracks.getListOfNewReleases();

    List<String> titles = new ArrayList<>();
    List<String> artists = new ArrayList<>();

    for(HashMap<String, String> i: topTracks) {
      titles.add(i.get("name"));
      artists.add(i.get("artist"));
    }
    Gson GSON = new Gson();

    if (titles != null) {
      // create an immutable map
      Map topTracksMap = ImmutableMap.of("topTracks", titles, "artists", artists);

      // return a json of the data
      return GSON.toJson(topTracksMap);
    } else {
      Map error = ImmutableMap.of("error", "Top tracks is null.");
      return GSON.toJson(error);
    }
  }
}
