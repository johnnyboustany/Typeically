package edu.brown.cs.student.main.spotify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

/**
 *
 * Class Responsible for Authenticating a Spotify APi Client to be used by relevant functions and classes
 * Use the client credentials flow when the requests don't require permission from a specific user.
 * This flow doesn't return a refresh token and is useful for simple requests, like fetching albums or searching for tracks.
 * Adapted from: https://github.com/spotify-web-api-java
 *
 */

public class SpotifyClient {

    // for initiating and retrieving the public ID from the secret folder
    private static final String  clientId = SpotifyAuth.getPublicId();

    // for initiating and retrieving the private ID from the secret folder
    private static final String  clientSecret =  SpotifyAuth.getPrivateId();

    // for creating an instance of the API
    public static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .build();

    // for creating a request
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    /**
     *
     * A method responsible for initializing the spotify client access token
     *
     */
    public static void clientCredentials() {
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
