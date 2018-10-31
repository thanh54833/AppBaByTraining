package com.example.thanh.appbabytraining.main.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thanh.appbabytraining.R;
import com.example.thanh.appbabytraining.Utils;
import com.example.thanh.appbabytraining.main.adapter.CustomAdapterAlarm;
import com.example.thanh.appbabytraining.main.adapter.CustomAdapterMain;
import com.example.thanh.appbabytraining.main.adapter.IAlarmItem;
import com.example.thanh.appbabytraining.main.adapter.IMusicItem;
import com.example.thanh.appbabytraining.main.object.IteamMusic;
import com.example.thanh.appbabytraining.main.object.ItemAlarm;
import com.example.thanh.appbabytraining.main.service.AlramService;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ActivityAlarm extends AppCompatActivity implements IMusicItem {


    ArrayList<IteamMusic> arrayList;
    ListView listView;
    private CustomAdapterAlarm adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm2);

        init();

        //setConfig();
    }

    private void init() {
        listView = findViewById(R.id.Lv_Music);
        arrayList = new ArrayList<>();
        arrayList = getListRaw();
        adapter = new CustomAdapterAlarm(arrayList, getApplicationContext(),this);
        listView.setAdapter(adapter);
    }

    public ArrayList<IteamMusic> getListRaw() {
        ArrayList<IteamMusic> iteamMusics = new ArrayList<>();
        Field[] fields = R.raw.class.getFields();

        for (int count = 0; count < fields.length; count++) {
            if(getConfig().equalsIgnoreCase(fields[count].getName())){
                iteamMusics.add(new IteamMusic(fields[count].getName(), true));
            }
            else {
                iteamMusics.add(new IteamMusic(fields[count].getName(), false));
            }


        }
        Utils.messageDisplay("get list music ...");
        return iteamMusics;
    }
    private String getConfig() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("configalarm", Context.MODE_PRIVATE);
        String name=null;
        if(sharedPreferences!=null){
            name=sharedPreferences.getString(getString(R.string.music_alram),"null");
        }
        return name;
    }
    public void setConfig(String name){
        SharedPreferences sharedPreferences = this.getSharedPreferences("configalarm", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(getString(R.string.music_alram), name);
            editor.commit();
        }
        Utils.messageDisplay("Click Save ...");
    }
    @Override
    public void onCheck(int position) {

        setConfig(arrayList.get(position).getName());

        for(IteamMusic iteamMusic:arrayList){
            iteamMusic.setFlag(false);
        }
        arrayList.get(position).setFlag(true);
        adapter.isAnamation=false;

        adapter.notifyDataSetChanged();
        Utils.messageDisplay("click button check ...");

    }

    public void onClickAlarm(View view){

        switch (view.getId()){
            case R.id.btn_back:
                startActivity(new Intent(getApplicationContext(),ActivitySetting.class));
                Toast.makeText(getApplicationContext(),"on click back...",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
