package com.example.application;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

public class songPage extends AppCompatActivity {
    TextView initialTime,totalTime;
    ImageView songIcon;
    ImageButton start,stop,pause,prevSong,nextSong;
    MediaPlayer music;
    SeekBar seekBar;
    ArrayList<MyListt> item;
    Handler handler;
    int position;
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_page);
        item= (ArrayList<MyListt>) getIntent().getSerializableExtra("List");
        position = getIntent().getIntExtra("position",0);
        handler = new Handler();
        songIcon = (ImageView)findViewById(R.id.songIcon);
        start = (ImageButton) findViewById(R.id.playButton);
        stop = (ImageButton) findViewById(R.id.stopButton);
        pause= (ImageButton) findViewById(R.id.pauseButton);
        prevSong= (ImageButton) findViewById(R.id.prevSong);
        nextSong= (ImageButton) findViewById(R.id.nextSong);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        initialTime = (TextView)findViewById(R.id.initialTime);
        totalTime = (TextView)findViewById(R.id.totalTime);
        setSongPage();
        prevSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevSong();
            }
        });
        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextSong();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSong();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseSong();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSong();
            }
        });
    }

    void setSongPage(){

        Uri artworkUri = Uri.parse("content://media/external/audio/media/"+item.get(position).getId()+"/albumart");
        songIcon.setImageURI(artworkUri);
        totalTime.setText(createTime(item.get(position).getDuration()));

    }

    private void playSong(){
        Uri pathUri=  Uri.parse("content://media/external/audio/media/"+item.get(position).getId());
        if(music == null){
            music = MediaPlayer.create(getApplicationContext(),pathUri);
            music.start();
        }
        if(!music.isPlaying()){
            music.start();
        }

        initializeSeekBar();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(music!=null && b){
                    music.seekTo(i*1000);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    private void pauseSong(){
        if(music !=null){
            music.pause();
        }
    }
    private void stopSong(){
        if(music != null){
            music.release();
            music = null;
            if(handler!=null){
                handler.removeCallbacks(runnable);
            }
        }
    }
    private void prevSong(){
        stopSong();
        if(position-1 >=0){
            position-=1;
        }
        if(music != null){
            music.reset();
        }
        setSongPage();
        playSong();
    }
    private void nextSong(){
        stopSong();
        if(position+1 <item.size()){
            position+=1;
        }
        if(music != null){
            music.reset();
        }
        setSongPage();
        playSong();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(music != null){
            music.release();
            music = null;
        }
    }

    protected void initializeSeekBar(){
        seekBar.setMax(music.getDuration()/1000);
        runnable = new Runnable() {
            @Override
            public void run() {
                if(music!=null){
                    int currentPosition = music.getCurrentPosition()/1000;
                    seekBar.setProgress(currentPosition);
                    initialTime.setText(createTime(music.getCurrentPosition()));
                }
                handler.postDelayed(runnable,1000);
            }
        };
        handler.postDelayed(runnable,1000);
    }
    private String createTime(int time){
        String duration = "";
        int durationSeconds = (time/1000)%60;
        int durationMinutes = (time/1000/60);

        duration+=durationMinutes +":";
        if(durationSeconds<10){
            duration += "0";
        }
        duration+= durationSeconds;
        return duration;
    }


}