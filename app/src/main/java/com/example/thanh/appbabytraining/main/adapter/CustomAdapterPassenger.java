package com.example.thanh.appbabytraining.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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

import com.example.mylibrary.CustomBitmap;
import com.example.thanh.appbabytraining.R;
import com.example.thanh.appbabytraining.main.object.IteamMusic;
import com.example.thanh.appbabytraining.main.object.IteamPassenger;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapterPassenger extends ArrayAdapter<IteamPassenger> {

    private ArrayList<IteamPassenger> dataSet;
    private Context mContext;
    private IPassengerIteam iPassengerIteam;
    public static boolean isAnamation=true;

    // View lookup cache
    private static class ViewHolder {

        ImageView iv_child;
        TextView tv_name;
        Button btn_delete;
    }

    public CustomAdapterPassenger(ArrayList<IteamPassenger> data, Context context, IPassengerIteam iPassengerIteam) {

        super(context, R.layout.row_item_home, data);
        this.dataSet = data;
        this.mContext = context;
        this.iPassengerIteam=iPassengerIteam;

    }

    private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        IteamPassenger dataModel = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_passenger, parent, false);

            viewHolder.tv_name=convertView.findViewById(R.id.tv_name);
            viewHolder.iv_child=convertView.findViewById(R.id.iv_Child);
            viewHolder.btn_delete=convertView.findViewById(R.id.btn_delete);

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

        Bitmap bitmap=BitmapFactory.decodeResource(convertView.getResources(),R.drawable.image);

        RoundedBitmapDrawable roundedBitmapDrawable=RoundedBitmapDrawableFactory.create(convertView.getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);

        viewHolder.iv_child.setImageDrawable(roundedBitmapDrawable);
       // viewHolder.iv_child.


        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(getContext(),"on click ...",Toast.LENGTH_SHORT).show();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }



}