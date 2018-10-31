package com.example.thanh.appbabytraining.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thanh.appbabytraining.R;
import com.example.thanh.appbabytraining.main.object.IteamMusic;
import com.example.thanh.appbabytraining.main.object.ItemAlarm;

import java.util.ArrayList;

public class CustomAdapterAlarm extends ArrayAdapter<IteamMusic> {

    private ArrayList<IteamMusic> dataSet;
    private Context mContext;
    private IMusicItem iMusicItem;
    public static boolean isAnamation=true;

    // View lookup cache
    private static class ViewHolder {
        TextView txt_name;
        Button bnt_check;
    }

    public CustomAdapterAlarm(ArrayList<IteamMusic> data, Context context,IMusicItem iMusicItem) {

        super(context, R.layout.row_item_home, data);
        this.dataSet = data;
        this.mContext = context;
        this.iMusicItem=iMusicItem;


    }

    private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        IteamMusic dataModel = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_music, parent, false);

            viewHolder.txt_name=convertView.findViewById(R.id.txt_name);
            viewHolder.bnt_check=convertView.findViewById(R.id.btn_check);


            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        if(isAnamation){
            Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            result.startAnimation(animation);
            lastPosition = position;
        }
        viewHolder.txt_name.setText(dataModel.getName());

        if(dataModel.isFlag()==false){
            viewHolder.bnt_check.setBackgroundResource(R.drawable.uncheck);
        }else {
            viewHolder.bnt_check.setBackgroundResource(R.drawable.checked);
        }

        viewHolder.bnt_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iMusicItem.onCheck(position);

                Toast.makeText(getContext(),"Click button item... : "+position,Toast.LENGTH_SHORT).show();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}