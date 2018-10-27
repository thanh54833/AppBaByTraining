package com.example.thanh.appbabytraining.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.thanh.appbabytraining.R;
import com.example.thanh.appbabytraining.main.adapter.CustomAdapter;
import com.example.thanh.appbabytraining.main.object.ItemAlarm;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    ArrayList<ItemAlarm> arrayList;
    ListView listView;
    private CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        listView=findViewById(R.id.list);
        arrayList=new ArrayList<>();

        for(int i=0;i<10;i++){
            ItemAlarm itemAlarm=new ItemAlarm(100,new Intent(getApplicationContext(),AlramService.class),100,"music","image",true,"icon","decscription","timeRemain");
            arrayList.add(itemAlarm);
        }
        adapter= new CustomAdapter(arrayList,getApplicationContext());
        listView.setAdapter(adapter);
    }
}
