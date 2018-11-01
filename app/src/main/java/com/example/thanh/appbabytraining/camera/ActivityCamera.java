package com.example.thanh.appbabytraining.camera;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mylibrary.Files;
import com.example.thanh.appbabytraining.R;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityCamera extends AppCompatActivity implements IAddChild {

    private Camera mCamera;
    private CameraPreview mPreview;
    private Camera.PictureCallback mPicture;

    private Context myContext;
    private LinearLayout cameraPreview;
    private boolean cameraFront = false;
    public static Bitmap bitmap;
    private Button btn_circle;
    private ImageView iv_circle;
    private Button btnCam;
    private int rotate = 90;
    private static int TIME = 0;
    private TextView tv_time;
    private Button btn_plus,btn_minus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        init();
    }

    private void init() {
        tv_time = findViewById(R.id.tv_time);
        tv_time.setText(TIME + " Seconds ");
        btn_plus=findViewById(R.id.btn_plus);
        btn_minus=findViewById(R.id.bnt_minus);
        cameraPreview = (LinearLayout) findViewById(R.id.cPreview);
        myContext = this;
        btn_circle = findViewById(R.id.btn_circle_image);
        // btn_circle.
        iv_circle = findViewById(R.id.iv_circle);
        btnCam = findViewById(R.id.btnCam);

        btnCam.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        iv_circle.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
                        iv_circle.setVisibility(View.GONE);

                        break;
                }
                return false;
            }
        });

        config();
    }

    private void config() {
        mCamera = Camera.open();
        mCamera.setDisplayOrientation(rotate);
        mPicture = getPictureCallback();
        mPreview = new CameraPreview(myContext, mCamera);
        cameraPreview.addView(mPreview);
        mCamera.startPreview();
    }

    public void onClickCapture(View view) {
        switch (view.getId()) {
            case R.id.btn_circle_image:

                startActivity(new Intent(getApplicationContext(),Activity_Select_Image.class));

                Toast.makeText(getApplicationContext(), "Click circle image ...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_Switch:

                int camerasNumber = Camera.getNumberOfCameras();
                if (camerasNumber > 1) {
                    //release the old camera instance
                    //switch camera, from the front and the back and vice versa
                    releaseCamera();
                    chooseCamera();
                } else {

                }
                Toast.makeText(getApplicationContext(), "click capture image ...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bnt_minus:

                if (TIME > 0) {
                    TIME--;
                    tv_time.setText(TIME + " Seconds ");
                }
                Toast.makeText(getApplicationContext(), "Click minus time ...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_plus:

                TIME++;
                tv_time.setText(TIME + " Seconds ");
                Toast.makeText(getApplicationContext(), "click plust time ...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnCam:
                if (TIME == 0) {
                    mCamera.takePicture(null, null, mPicture);
                } else {

                    final long totaTime=TIME*1000;
                    new CountDownTimer(totaTime, 1000) {
                        @Override
                        public void onTick(long l) {
                            tv_time.setText((int)((l)/1000)+" Seconds ");
                            tv_time.setTextColor(getResources().getColor(R.color.colorBlue));
                            //Toast.makeText(getApplicationContext(),"time :"+(totaTime-l),Toast.LENGTH_SHORT).show();
                           // Log.i("time.camera","Time :"+(totaTime-l)+" totalTime :"+totaTime+ "long :"+));
                            btn_plus.setVisibility(View.GONE);
                            btn_minus.setVisibility(View.GONE);

                            btnCam.setEnabled(false);

                        }
                        @Override
                        public void onFinish() {
                            mCamera.takePicture(null, null, mPicture);
                            TIME=0;
                            tv_time.setText(TIME+" Seconds ");
                            tv_time.setTextColor(getResources().getColor(R.color.colorWhite));

                            btn_plus.setVisibility(View.VISIBLE);
                            btn_minus.setVisibility(View.VISIBLE);

                            btnCam.setEnabled(true);
                        }
                    }.start();
                }
                Toast.makeText(getApplicationContext(), "click capture image ...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                cameraFront = true;
                break;
            }
        }
        return cameraId;
    }

    private int findBackFacingCamera() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                cameraFront = false;
                break;
            }
        }
        return cameraId;
    }

    public void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = Camera.open();
            mCamera.setDisplayOrientation(rotate);
            mPicture = getPictureCallback();
            mPreview.refreshCamera(mCamera);
            Log.d("nu", "null");
        } else {
            Log.d("nu", "no null");
        }
    }

    public void chooseCamera() {

        if (cameraFront) {
            int cameraId = findBackFacingCamera();
            if (cameraId >= 0) {

                mCamera = Camera.open(cameraId);
                mCamera.setDisplayOrientation(rotate);
                mPicture = getPictureCallback();
                mPreview.refreshCamera(mCamera);

            }
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId >= 0) {
                mCamera = Camera.open(cameraId);
                mCamera.setDisplayOrientation(rotate);
                mPicture = getPictureCallback();
                mPreview.refreshCamera(mCamera);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //when on Pause, release camera in order to be used from other applications
        releaseCamera();
    }

    private void releaseCamera() {
        // stop and release camera
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Camera.PictureCallback getPictureCallback() {
        Camera.PictureCallback picture = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {


                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                try {

                    Bitmap realImage = BitmapFactory.decodeByteArray(data, 0, data.length);

                    realImage = rotate(realImage, 90);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    realImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), realImage);
                    roundedBitmapDrawable.setCircular(true);

                    btn_circle.setBackground(roundedBitmapDrawable);

                    writeInternal(stream.toByteArray(),timeStamp+".png");
                } catch (Exception e) {
                    Log.d("Info", "File not found: " + e.getMessage());
                }
            }
        };
        return picture;
    }
    public void writeInternal(byte[] buffer,String fileName) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            fileOutputStream.write(buffer);
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(), "Text Saved", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        //       mtx.postRotate(degree);
        mtx.setRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }


    private Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public void onClick(int position) {

    }
}
