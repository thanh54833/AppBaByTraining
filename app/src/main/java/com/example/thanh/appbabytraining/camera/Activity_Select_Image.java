package com.example.thanh.appbabytraining.camera;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.thanh.appbabytraining.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Activity_Select_Image extends AppCompatActivity implements IAddChild{

    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView im_image;

    private ArrayList<ImageSelect> imageSelects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__select__image);



        im_image = findViewById(R.id.imgView);

        //write( "thanh ne !".getBytes(),"text.txt");
        File dir = getFilesDir();
        File[] list = dir.listFiles();
        int length = 0;

        imageSelects = new ArrayList<>();
        for (File ff : list) {

            if (ff.getName().indexOf(".png") >= 0) {

                imageSelects.add(new ImageSelect(ff.getName(), ff));
                Log.d("image.thanh", "path :" + ff);
                length++;
            }
            Log.d("name.name", "name :" + ff.getName());
        }

        GridView gridView = findViewById(R.id.gridview);

        if (imageSelects.size() >= 9) {
            gridView.getLayoutParams().height = 900;
        } else if (imageSelects.size() <= 6) {
            gridView.getLayoutParams().height = 750;
        }

        CustomAdapterSelect customAdapterSelect = new CustomAdapterSelect(imageSelects, getApplicationContext(),this);
        gridView.setAdapter(customAdapterSelect);
        Toast.makeText(getApplicationContext(), "lenght :" + length, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();




            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    public void onClickSelection(View view) {

        switch (view.getId()) {
            case R.id.btn_back:

                Toast.makeText(getApplicationContext(), "click back ...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_external:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                Toast.makeText(getApplicationContext(), "click external ...", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onClick(int position) {

        imageSelects.get(position);

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
