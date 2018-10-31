package com.example.thanh.appbabytraining.main.view;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thanh.appbabytraining.R;
import com.example.thanh.appbabytraining.main.adapter.CustomAdapterPassenger;
import com.example.thanh.appbabytraining.main.adapter.IPassengerIteam;
import com.example.thanh.appbabytraining.main.object.IteamPassenger;

import java.io.File;
import java.util.ArrayList;

public class ActivityPassenger extends AppCompatActivity implements IPassengerIteam{


    private ListView listView;
    private CustomAdapterPassenger customAdapterPassenger;
    ArrayList<IteamPassenger>iPassengerIteams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
        iPassengerIteams=new ArrayList<>();

        for(int i=0;i<=10;i++){

            File file=Environment.getExternalStorageDirectory();
            IteamPassenger iteamPassenger=new IteamPassenger(file,"thanh");
            iPassengerIteams.add(iteamPassenger);
        }

        customAdapterPassenger=new CustomAdapterPassenger(iPassengerIteams,getApplicationContext(),this);

        listView=findViewById(R.id.list);

        listView.setAdapter(customAdapterPassenger);

    }

    public void onClickPassenger(View view){
        switch (view.getId()){
            case R.id.btn_add_item:

                Toast.makeText(getApplicationContext(),"click add item ...",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClickDelete(int position) {

    }


}
