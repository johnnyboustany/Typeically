package edu.brown.cs.student.main.spotify;

import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.browse.GetListOfNewReleasesRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TopTracks {


  public void LikedSongs(){}


  public List<HashMap<String, String>> getListOfNewReleases() {
    SpotifyClient client = new SpotifyClient();
    client.clientCredentials();
    //this is important to ensure access token is valid
    GetListOfNewReleasesRequest getListOfNewReleasesRequest = client.spotifyApi.getListOfNewReleases()
//          .country(CountryCode.SE)
//          .limit(10)
//          .offset(0)
        .build();
    List<HashMap<String, String>> resultFinal = new ArrayList<>();
    try {
      final Paging<AlbumSimplified> albumSimplifiedPaging = getListOfNewReleasesRequest.execute();
      AlbumSimplified[] resultOne = albumSimplifiedPaging.getItems();
      for (AlbumSimplified i:
          resultOne) {
        String name = i.getName();
        String id = i.getId();
        String URI = i.getUri();
        String artist = i.getArtists()[0].getName();
        HashMap<String, String> resultTemp = new HashMap<>();
        resultTemp.put("name", name);
        resultTemp.put("id", id);
        resultTemp.put("URI", URI);
        resultTemp.put("artist", artist);
        resultFinal.add(resultTemp);
      }
      System.out.println("Total: " + albumSimplifiedPaging.getTotal());
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      System.out.println("Error: " + e.getMessage());
    }
    return resultFinal;
  }






}