package com.example.thanh.appbabytraining.main.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.thanh.appbabytraining.R;
import com.example.thanh.appbabytraining.Utils;

public class ActivitySetting extends AppCompatActivity {


    private Switch sw_vibreate;
    private SharedPreferences sharedPreferences;
    private int volume;
    private SeekBar sb_volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sharedPreferences = this.getSharedPreferences("configalarm", Context.MODE_PRIVATE);
        sw_vibreate = findViewById(R.id.sw_vibrate);
        sb_volume=findViewById(R.id.sb_volume);

        if(getConfigVabrate()){
            sw_vibreate.setChecked(true);
        }
        else {
            sw_vibreate.setChecked(false);
        }

        sb_volume.setProgress(volume);

        sb_volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                volume=seekBar.getProgress();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(getString(R.string.music_volume), volume);
                editor.commit();

                Toast.makeText(getApplicationContext(),"on stop ..:"+volume,Toast.LENGTH_SHORT).show();
            }
        });


        sw_vibreate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (sharedPreferences != null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(getString(R.string.music_vibrate), b);
                    editor.commit();
                }
            }
        });

    }

    public void onClickSetting(View view) {

        switch (view.getId()) {
            case R.id.btn_arrow_manager:

                Toast.makeText(getApplicationContext(), "Arrow manager ...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_arrow_selection:

                startActivity(new Intent(getApplicationContext(),ActivityAlarm.class));
                Toast.makeText(getApplicationContext(), "Arrow selection ...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_record:

                Toast.makeText(getApplicationContext(), "click record ...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_back:

                //startActivity(new Intent(getApplicationContext(),Main.class));

                Toast.makeText(getApplicationContext(), "click back ...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private boolean getConfigVabrate() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("configalarm", Context.MODE_PRIVATE);
        boolean is = false;
        if(sharedPreferences!=null){
            is=sharedPreferences.getBoolean(getString(R.string.music_vibrate),false);
            volume=sharedPreferences.getInt(getString(R.string.music_volume),90);
        }
        return is;
    }


}
