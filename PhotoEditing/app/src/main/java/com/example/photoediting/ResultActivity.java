package com.example.photoediting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.photoediting.databinding.ActivityResultBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityResultBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Toast.makeText(this, ""+getIntent().getStringExtra("data"), Toast.LENGTH_SHORT).show();
        binding.image.setImageURI(Uri.parse(getIntent().getStringExtra("data")));
        //Log.d("image uri" , getIntent().getData().toString());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(ResultActivity.this,MainActivity.class);
                startActivity(i);
            }
        });


        binding.sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse(getIntent().getStringExtra("data")) );
                startActivity(Intent.createChooser(sharingIntent, "Share image using"));
            }
        });


    }

//    private Uri getLocalBitmapUri(Bitmap bmp)
//    {
//        Uri bmpUri = null;
//        try {
//            File file  = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),"wallpaper" +System.currentTimeMillis() + ".png");
//            FileOutputStream out =new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.PNG,90,out);
//            out.close();
//            bmpUri=Uri.fromFile(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bmpUri;
//    }
//   no bit mat required because allready bitmap provide by main activity then send here image
}