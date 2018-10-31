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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thanh.appbabytraining.R;
import com.example.thanh.appbabytraining.main.object.ItemAlarm;

import java.util.ArrayList;

public class CustomAdapterMain extends ArrayAdapter<ItemAlarm> {

    private ArrayList<ItemAlarm> dataSet;
    private Context mContext;
    private IAlarmItem iAlarmItem;

    // View lookup cache
    private static class ViewHolder {

        ImageView img_flag;
        TextView txt_word;
        TextView txt_description;
        Button btn_icon;
        Button btn_time;
        TextView txt_time;
        LinearLayout lay_item;

    }

    public CustomAdapterMain(ArrayList<ItemAlarm> data, Context context, IAlarmItem iAlarmItem) {

        super(context, R.layout.row_item_home, data);
        this.dataSet = data;
        this.mContext = context;
        this.iAlarmItem = iAlarmItem;

    }

    private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ItemAlarm dataModel = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_home, parent, false);

            viewHolder.img_flag = (ImageView) convertView.findViewById(R.id.img_flag);
            viewHolder.txt_word = (TextView) convertView.findViewById(R.id.txt_work);
            viewHolder.txt_description = (TextView) convertView.findViewById(R.id.txt_decription);
            viewHolder.btn_icon = (Button) convertView.findViewById(R.id.btn_icon);
            viewHolder.btn_time = (Button) convertView.findViewById(R.id.btn_time);
            viewHolder.txt_time = (TextView) convertView.findViewById(R.id.txt_time);
            viewHolder.lay_item = (LinearLayout) convertView.findViewById(R.id.lay_item);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.lay_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iAlarmItem.startService(position);
            }
        });

        viewHolder.btn_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iAlarmItem.stopService(position);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}