package com.example.application;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

public class MBroadcastReceiver extends BroadcastReceiver {
    private AudioManager MAudioManager;
    private Context con;
    @Override
    public void onReceive(Context context, Intent intent) {
        con = context.getApplicationContext();
        MAudioManager = (AudioManager)con.getSystemService(Context.AUDIO_SERVICE);
        if("com.example.application.Broadcast".equals(intent.getAction())){
            String state = intent.getStringExtra("state");
                if(state.equals("turnOn")){
                    MAudioManager.adjustVolume(AudioManager.ADJUST_UNMUTE,AudioManager.FLAG_PLAY_SOUND);
                }else {
                    MAudioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.FLAG_PLAY_SOUND);
                }

        }
    }
}
