package com.example.task2;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.task1.R;

import java.util.HashMap;
/**
 * @author lfh
 * @project Task1
 * @package_name com.example.task2
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
public class Activity2 extends FragmentActivity {
    private String rings[]={"bird","chimes","cock","cuckoo","notify","ringout","water"};
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);

        //create sound effect  attributes object
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)   //set sound effect usage
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)  //set sound effect type
                .build();

        final SoundPool soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(10)
                .build();

        final HashMap<Integer,Integer> soundMap = new HashMap<>();
        soundMap.put(0,soundPool.load(this,R.raw.bird,1));
        soundMap.put(1, soundPool.load(this, R.raw.chimes, 1));
        soundMap.put(2,soundPool.load(this,R.raw.cock,1));
        soundMap.put(3,soundPool.load(this,R.raw.cuckoo,1));
        soundMap.put(4,soundPool.load(this,R.raw.notify,1));
        soundMap.put(5,soundPool.load(this,R.raw.ringout,1));
        soundMap.put(6,soundPool.load(this,R.raw.water,1));

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rings);
        listView = (ListView) findViewById(R.id.ringList);
        listView.setAdapter(stringArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                soundPool.play(soundMap.get(i), 1, 1, 0, 0, 1);
            }
        });
    }
}
