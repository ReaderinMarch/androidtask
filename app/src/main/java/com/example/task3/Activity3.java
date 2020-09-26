package com.example.task3;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.task1.R;

import java.io.File;

public class Activity3 extends FragmentActivity {
    private VideoView videoView;
    private MediaController mediaController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout3);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        videoView = findViewById(R.id.video);

        String path = Environment.getExternalStorageDirectory().getPath()+"/Download/videotest.mp4";
        File file = new File(path);
        if (file.exists()) {
            videoView.setVideoPath(file.getAbsolutePath());}
            else{
            Toast.makeText(this,"file is not exist",Toast.LENGTH_SHORT).show();
            }
        mediaController = new MediaController(Activity3.this);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(Activity3.this,"video finish ",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
