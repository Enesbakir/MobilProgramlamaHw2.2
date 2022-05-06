package com.example.application;

import java.io.File;
import java.io.Serializable;

public class MyListt implements Serializable {
    private String songWriterName;
    private String songTitle;
    private String displayName;
    private int iconId;
    private long id;
    private long albumId;
    private String path;
    private int duration;

    public MyListt() {
    }

    public MyListt(long id, String songTitle, String songWriterName, long albumId,String path,int duration,String displayName){
        this.id = id;
        this.iconId=R.drawable.ic_player;
        this.songTitle=songTitle;
        this.songWriterName = songWriterName;
        this.albumId=albumId;
        this.path = path;
        this.duration=duration;
        this.displayName =displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public long getAlbumId() {
        return albumId;
    }
    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
    public int getDuration() {
        return duration;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getSongWriterName() {
        return songWriterName;
    }
    public String getSongTitle() {
        return songTitle;
    }
    public int getIconId() {
        return iconId;
    }



}

