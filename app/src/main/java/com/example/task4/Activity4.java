package com.example.task4;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.task1.R;

import java.io.IOException;

public class Activity4 extends FragmentActivity implements View.OnClickListener
{
    private ImageButton btn_play;
    private ImageButton btn_pause;
    private ImageButton btn_stop;
    private boolean noPlay = true;//定义播放状态
    private MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout4);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SurfaceView surfaceView=findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(Activity4.this,"视频播放完毕",Toast.LENGTH_SHORT).show();
            }
        });
        btn_play = findViewById(R.id.btn_play);
        btn_pause = findViewById(R.id.btn_pause);
        btn_stop = findViewById(R.id.btn_stop);
        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_play:
                if (noPlay){
                    try {
                        play();
                        noPlay=false;//视频处于播放状态
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    mediaPlayer.start();//继续播放视频
                }
                break;
            case R.id.btn_pause:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case R.id.btn_stop:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    noPlay=true;
                }
                break;

        }

    }
    public void play() throws IOException {
        //重制mediaplayer对象
        mediaPlayer.reset();
        //把视频画面输出到surfaceview
        mediaPlayer.setDisplay(surfaceHolder);
        mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/Download/videotest.mp4");
        mediaPlayer.prepare();
        mediaPlayer.start();
    }
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){
            if (mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
    }
}
