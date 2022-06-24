package edu.brown.cs.student.main.spotify;

import java.util.List;

/*
*
* A class for representing a final track and its attributes
*
*/
public class TrackInfo {
    private String id;
    private String name;
    private String SpotifyURI;
    private int DurationMs;
    private String album;
    private List<String> artists;
    private int popularity;
    private boolean explicit;
    private float acousticness;
    private float danceability;
    private float energy;
    private float liveness;
    private float loudness;
    private float speechness;
    private float tempo;
    private float valance;


    /*
     *
     * A class constructor
     *
     */
    public TrackInfo(){}

    /*
    *
    * A setter for ID
    *
    */
    public void setID(String trackID) {
        this.id = id;
    }

    /*
     *
     * A setter for name
     *
     */
    public void setName(String trackName) {
        this.name = trackName;
    }

    /*
     *
     * A setter for spotify URI
     *
     */
    public void setURI(String uri) {
        this.SpotifyURI = uri;
    }

    /*
     *
     * A setter for spotify duration
     *
     */
    public void setDuration(int duration) {
        this.DurationMs = duration;
    }

    /*
     *
     * A setter for album
     *
     */
    public void setAlbum(String Trackalbum) {
        this.album = Trackalbum;
    }

    /*
     *
     * A setter for artists
     *
     */
    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    /*
     *
     * A setter for popularity
     *
     */
    public void setPopularity(int pop) {
        this.popularity = pop;
    }

    /*
     *
     * A setter for explicit
     *
     */
    public void setExplicit(boolean exp) {
        this.explicit = exp;
    }

    /*
     *
     * A setter for acousticness
     *
     */
    public void setAcoustic(float acousticness) {
        this.acousticness = acousticness;
    }

    /*
     *
     * A setter for danceability
     *
     */
    public void setDanceability(float danceability) {
        this.danceability = danceability;
    }

    /*
     *
     * A setter for energy
     *
     */
    public void setEnergy(float energy) {
        this.energy = energy;
    }

    /*
     *
     * A setter for liveness
     *
     */
    public void setLiveness(float liveness) {
        this.liveness = liveness;
    }

    /*
     *
     * A setter for loudness
     *
     */
    public void setLoudness(float loudness) {
        this.loudness = loudness;
    }

    /*
     *
     * A setter for speechness
     *
     */
    public void setSpeechness(float speechness) {
        this.speechness = speechness;
    }

    /*
     *
     * A setter for tempo
     *
     */
    public void setTempo(float tempo) {
        this.tempo = tempo;
    }

    /*
     *
     * A setter for valance
     *
     */
    public void setValance(float valance) {
        this.tempo = valance;
    }

    /*
     *
     * A getter for ID
     *
     */
    public String getID() {
        return this.id;
    }

    /*
     *
     * A getter for name
     *
     */
    public String getName() {
        return this.name;
    }

    /*
     *
     * A getter for spotify URI
     *
     */
    public String getURI() {
        return this.SpotifyURI;
    }

    /*
     *
     * A getter for spotify duration
     *
     */
    public int getDuration() {
        return this.DurationMs;
    }

    /*
     *
     * A getter for album
     *
     */
    public String getAlbum() {
        return this.album;
    }

    /*
     *
     * A getter for artists
     *
     */
    public List<String> getArtists() {
        return this.artists;
    }

    /*
     *
     * A getter for popularity
     *
     */
    public int getPopularity() {
        return this.popularity;
    }

    /*
     *
     * A getter for explicit
     *
     */
    public boolean getExplicit() {
        return this.explicit;
    }

    /*
     *
     * A getter for acousticness
     *
     */
    public float getAcoustic() {
        return this.acousticness;
    }

    /*
     *
     * A getter for danceability
     *
     */
    public float getDanceability() {
        return this.danceability;
    }

    /*
     *
     * A getter for energy
     *
     */
    public float getEnergy() {
        return this.energy;
    }

    /*
     *
     * A getter for liveness
     *
     */
    public float getLiveness() {
        return this.liveness;
    }

    /*
     *
     * A getter for loudness
     *
     */
    public float setLoudness() {
        return this.loudness;
    }

    /*
     *
     * A getter for speechness
     *
     */
    public float getSpeechness() {
        return this.speechness;
    }

    /*
     *
     * A getter for tempo
     *
     */
    public float getTempo() {
        return this.tempo;
    }

    /*
     *
     * A getter for valance
     *
     */
    public float getValance() {
        return this.tempo;
    }

    public String trackToString() {
        System.out.println("HERE");
        StringBuilder sb = new StringBuilder();
        sb.append(this.id);
        sb.append(" , ");
        sb.append(this.name);
        sb.append(" , ");
        sb.append(this.name);
        sb.append(" , ");
        sb.append(this.DurationMs);
        sb.append(" , ");
        sb.append(album);
        sb.append(" , ");
        sb.append(popularity);
        sb.append(" , ");
        sb.append(explicit);
        sb.append(" , ");
        sb.append(energy);
        sb.append(" , ");
        sb.append(liveness);
        sb.append(" , ");
        sb.append(acousticness);
        sb.append(" , ");
        sb.append(danceability);
        sb.append(" , ");
        sb.append(loudness);
        sb.append(" , ");
        sb.append(speechness);
        sb.append(" , ");
        sb.append(tempo);
        sb.append(" , ");
        sb.append(valance);
        System.out.println(sb.toString());
        return sb.toString();
    }



}
