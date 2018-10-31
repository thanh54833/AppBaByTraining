package com.example.thanh.appbabytraining.main.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import com.example.thanh.appbabytraining.R;
import com.example.thanh.appbabytraining.main.adapter.CustomAdapterMain;
import com.example.thanh.appbabytraining.main.adapter.IAlarmItem;
import com.example.thanh.appbabytraining.main.object.ItemAlarm;
import com.example.thanh.appbabytraining.main.service.AlramService;

import java.util.ArrayList;

public class Main extends AppCompatActivity implements IAlarmItem {

    ArrayList<ItemAlarm> arrayList;
    ListView listView;
    private CustomAdapterMain adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        init();
    }

    private Intent intent;

    private void init() {
        listView = findViewById(R.id.list);

        arrayList = new ArrayList<>();
        intent = new Intent(getApplicationContext(), AlramService.class);

        for (int i = 0; i < 10; i++) {

            ItemAlarm itemAlarm = new ItemAlarm(i, intent, 2, "music", "image", false, "icon", "decscription", "timeRemain");
            arrayList.add(itemAlarm);
        }

        adapter = new CustomAdapterMain(arrayList, getApplicationContext(), this);
        listView.setAdapter(adapter);

    }

    private Intent intents;

    @Override
    public void startService(int posiontion) {

        if (arrayList.get(posiontion).isFlag() == false) {

            intents = new Intent(getApplicationContext(), AlramService.class);

            intents.putExtra("name","thanh");
            intents.putExtra(getString(R.string.TIME), arrayList.get(posiontion).getTime());
            intents.putExtra(getString(R.string.ID), arrayList.get(posiontion).getId());

            startService(intents);
            arrayList.get(posiontion).setFlag(true);

        }else {
            Toast.makeText(getApplicationContext(),"task on start ...",Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getApplicationContext(), "start service " + posiontion + "... :", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stopService(int posiontion) {

        intents = new Intent(getApplicationContext(), AlramService.class);
        startService(intents);

        Toast.makeText(getApplicationContext(), "stop service " + posiontion + "... :", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_add_item:

                Toast.makeText(getApplicationContext(),"click add item ...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_setting:
                Toast.makeText(getApplicationContext(),"click setting ...",Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
