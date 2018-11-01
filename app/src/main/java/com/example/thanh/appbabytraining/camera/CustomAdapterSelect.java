package com.example.thanh.appbabytraining.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.thanh.appbabytraining.main.adapter.IMusicItem;
import com.example.thanh.appbabytraining.main.object.IteamMusic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CustomAdapterSelect extends ArrayAdapter<ImageSelect> {

    private ArrayList<ImageSelect> dataSet;
    private Context mContext;
    private IAddChild iAddChild;

    public static boolean isAnamation=false;
    // View lookup cache
    private static class ViewHolder {
        ImageView iv_select;
        TextView tv_name;

    }
    public CustomAdapterSelect(ArrayList<ImageSelect> data, Context context,IAddChild iAddChild){
        super(context, R.layout.row_item_home, data);
        this.dataSet = data;
        this.mContext = context;
        this.iAddChild=iAddChild;
    }

    private int lastPosition = -1;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ImageSelect dataModel = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_select, parent, false);

            viewHolder.iv_select=convertView.findViewById(R.id.iv_picture);
            viewHolder.tv_name=convertView.findViewById(R.id.tv_name);


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

        viewHolder.tv_name.setText(dataModel.getName());

        try {
            viewHolder.iv_select.setImageBitmap(loadImageFromStorage(mContext, dataModel.getName()));
        }catch (Exception e){
            viewHolder.iv_select.setBackgroundResource(R.drawable.bg_active_route_in_finished_gradient);
        }

        viewHolder.iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iAddChild.onClick(position);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }


    private Bitmap loadImageFromStorage(Context context,String message) {
        try {
            File f = new File(context.getFilesDir(), message);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }




}