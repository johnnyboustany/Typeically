package edu.brown.cs.student.main.spotify;

import edu.brown.cs.student.main.REPL.REPLable;

import java.util.List;

public class LoadTracks implements REPLable {

    @Override
    public String getDescription() {
        String s = "this command is responsible for fetching a number of spotify " +
                "tracks that matches a user inputed query";
        return s;
    }

    @Override
    public void execute(List<String> args) {
        String query = "";
        String command = args.get(0);
        System.out.println(command);
        for(int i=1; i<args.size(); i++) {
            String temp = args.get(i);
            query = query + " " + temp;
        }
        System.out.println(query);
        SpotifyClient client = new SpotifyClient();
        //this is important to ensure acceess token is valid
        client.clientCredentials();
        SpotifyTrack GetTrack = new SpotifyTrack(query);
        List<TrackInfo> result = GetTrack.getTracks();
        System.out.println("the length of returned result is : ");
        System.out.println(result.size());
        for (TrackInfo track:
                result) {
            System.out.println(track.getName());
            System.out.println(track.getID());
        }

    }

}
