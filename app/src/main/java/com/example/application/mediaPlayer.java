package com.example.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

public class mediaPlayer extends AppCompatActivity {
    ImageButton playLists,allSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        playLists = (ImageButton)findViewById(R.id.allPlaylistButton);
        allSongs = (ImageButton)findViewById(R.id.allSongsButton);
        ArrayList<MyListt> data = new ArrayList<MyListt>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }

        ContentResolver contentResolver = getContentResolver();
        Uri uri =  MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = MediaStore.Audio.Media.TITLE+" ASC";
        Cursor cursor= contentResolver.query(uri,null,null,null,sortOrder);
        if(cursor == null){
        }else if(!cursor.moveToFirst()){
        }else{
            int titleColumn= cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int displayNameColumn= cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
            int writerColumn= cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int albumImgColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int  pathColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            do{
                long thisId= cursor.getLong(idColumn);
                String thisTitle= cursor.getString(titleColumn);
                String thisWriter = cursor.getString(writerColumn);
                String path = cursor.getString(pathColumn);
                String displayName=cursor.getString(displayNameColumn);
                int duration = cursor.getInt(durationColumn);
                long albumId = cursor.getInt(albumImgColumn);
                MyListt item = new MyListt(thisId,thisTitle,thisWriter,albumId,path,duration,displayName);
                data.add(item);
            }while(cursor.moveToNext());
        }
        RecyclerView recyclerView= (RecyclerView)findViewById(R.id.recyclerView);
        itemAdapter adapter =new itemAdapter(data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        allSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView recyclerView= (RecyclerView)findViewById(R.id.recyclerView);
                itemAdapter adapter =new itemAdapter(data);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
            }
        });

        /*playLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent (context,playlistPage.class);
                context.startActivity(intent);
            }
        });*/
    }






}