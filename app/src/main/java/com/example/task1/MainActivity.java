package com.example.task1;

import androidx.fragment.app.FragmentActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * @author lfh
 * @project Task1
 * @package_name com.example.task1
 * @date 20-9-13
 * @time 上午10:40
 * @year 2020
 * @month 09
 * @month_short 九月
 * @month_full 九月
 * @day 13
 * @day_short 星期日
 * @day_full 星期日
 * @hour 10
 * @minute 40
 */
public class MainActivity extends FragmentActivity {

    //declaration Object
    private MediaPlayer mediaPlayer;
    private ImageButton buttonPlay;
    private ImageButton buttonPause;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musicplay);

        //get  imageButton Object
        buttonPlay=findViewById(R.id.buttonPlay);
        buttonPause=findViewById(R.id.buttonPause);

        mediaPlayer = MediaPlayer.create(this,R.raw.alin_love);

        //set onClickListener
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayer.start();
            }
        });

        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayer.pause();
            }
        });

    }
}