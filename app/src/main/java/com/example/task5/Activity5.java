/**
 * @author lfh
 * @project Task1
 * @package_name PACKAGE_NAME
 * @date 20-9-13
 * @time 下午12:47
 * @year 2020
 * @month 09
 * @month_short 九月
 * @month_full 九月
 * @day 13
 * @day_short 星期日
 * @day_full 星期日
 * @hour 12
 * @minute 47
 */
package com.example.task5;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.PixelFormat;

import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.task1.MainActivity;
import com.example.task1.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Activity5 extends AppCompatActivity  implements View.OnClickListener{
    private Camera camera;//定义一个摄像头对象
    private boolean isPreview = false;//是否为预览状态
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private ImageButton takephoto;
    private ImageButton preview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout5);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (!android.os.Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED))
        {
            Toast.makeText(this, "请安装SD卡", Toast.LENGTH_SHORT).show();
        };

        surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();

        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        takephoto = findViewById(R.id.takephoto);
        preview = findViewById(R.id.preview);
        takephoto.setOnClickListener(this);
        preview.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takephoto:
                if(camera!=null)camera.takePicture(null,null,jpeg);
                break;
            case R.id.preview:
                if (!isPreview){
                    camera = Camera.open();
                    isPreview = true;
                }
                try{
                    camera.setPreviewDisplay(surfaceHolder);
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setPictureFormat(PixelFormat.JPEG);
                    parameters.set("jpeg-quality", 80);
                    camera.setParameters(parameters);
                    camera.startPreview();
                    camera.autoFocus(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
    final Camera.PictureCallback jpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            System.out.println(bytes.length + "@@@@@@@@@@@@@");
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            camera.stopPreview();
            isPreview=false;
            File appDir = new File(Environment.getExternalStorageDirectory(), "/DCIM");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = System.currentTimeMillis() + ".jpeg";
            File file = new File(appDir, fileName);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                MediaStore.Images.Media.insertImage(Activity5.this.getContentResolver(), file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Activity5.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "")));
            Toast.makeText(Activity5.this,"picture save as: "+file,Toast.LENGTH_SHORT).show();
            resetCamera();

        }
    };
    private void resetCamera() {
        if (!isPreview) {
            camera.startPreview();
            isPreview=true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}


