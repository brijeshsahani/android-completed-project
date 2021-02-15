package com.example.wallpaper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class selectitem extends AppCompatActivity {

    ImageView selectimg;
    TextView selecttxt;
    String Sname = "";

    Button setbtn,downloadbtn,sharebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectitem);

        selecttxt=findViewById(R.id.txtSname);
        Sname = getIntent().getStringExtra("Photoname");
        selecttxt.setText(Sname);

        selectimg=findViewById(R.id.textsimage);
        selectimg.setDrawingCacheEnabled(true);
        selectimg.buildDrawingCache();


        String img_uri=getIntent().getStringExtra("Poriginal"); // name on onclick extra in adapter
        Glide.with(this).load(img_uri).centerCrop().into(selectimg); // load image in another activity

//        for wallpaper set
        setbtn = findViewById(R.id.setbtn);

        setbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                Bitmap bitmap = ((BitmapDrawable)selectimg.getDrawable()).getBitmap();

                try {
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(selectitem.this, "wallpaper set successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

//        end wallpaper set


//for download image
        downloadbtn=findViewById(R.id.downloadbtn);

        downloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(img_uri);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            }
        });

// end download image

//share image

 sharebtn = findViewById(R.id.sharebtn);

 sharebtn.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {

         Glide.with(getApplicationContext()).asBitmap().load(img_uri).into(new SimpleTarget<Bitmap>() {
             @Override
             public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                 Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                 sharingIntent.setType("image/*");
                 sharingIntent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(resource));
                 startActivity(Intent.createChooser(sharingIntent, "Share image using"));
             }
         });
     }
 });



//end share

    }

    private Uri getLocalBitmapUri(Bitmap bmp)
    {
        Uri bmpUri = null;
        try {
            File file  = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),"wallpaper" +System.currentTimeMillis() + ".png");
            FileOutputStream out =new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG,90,out);
            out.close();
            bmpUri=Uri.fromFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}