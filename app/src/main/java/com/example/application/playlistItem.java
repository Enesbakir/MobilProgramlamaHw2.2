package com.example.application;

import java.io.Serializable;
import java.util.ArrayList;

public class playlistItem implements Serializable {
    private ArrayList<MyListt> songs;
    private String playlistName;

    public playlistItem(ArrayList<MyListt> songs, String playlistName) {
        this.songs = songs;
        this.playlistName = playlistName;
    }

    public ArrayList<MyListt> getSongs() {
        return songs;
    }
    public String getPlaylistName() {
        return playlistName;
    }

    public void setSongs(ArrayList<MyListt> songs) {
        this.songs = songs;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
}
