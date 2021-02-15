package com.example.wallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wallpaper.adapters.ImgAdapter;
import com.example.wallpaper.adapters.SelectedImgAdapter;
import com.example.wallpaper.api.RetrofitClient;
import com.example.wallpaper.models.imagepojo;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class selectedCategory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImgAdapter adapter;
    private  int pos;//for position
    ArrayList<imagepojo> imgsList=new ArrayList<>();
    String keys="2e29ac80ed9347b199364516639867815653f2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);

        pos=getIntent().getIntExtra("position",0); //for position

        recyclerView =findViewById(R.id.recyclerView3);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(selectedCategory.this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ImgAdapter(selectedCategory.this, imgsList, new ImgAdapter.ClickHandler() {
            @Override
            public void onButtonClick(int position) {

                imagepojo item = imgsList.get(position);
                Intent intent = new Intent(selectedCategory.this, selectitem.class);
                intent.putExtra("Photoname",item.getName());
                intent.putExtra("Poriginal",item.getOriginal());

                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);




        backTask bg = new backTask();
        bg.execute();
    }

    class backTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {


//

            final String AUTH= "Basic "+ Base64.encodeToString(("phone4kwallpaper@gmail.com:phone4kwallpaper").getBytes(),Base64.NO_WRAP);

            Call call= RetrofitClient.getmInstance().getApi().getwallpaper(keys,AUTH);

            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

         // Toast.makeText(selectedCategory.this,response.body().toString(),Toast.LENGTH_SHORT).show();
//
              Log.e("55", "category 5 done: "+response.body() );

                    if(response.isSuccessful())
                    {
                        Log.d("in success 55" , response.toString());
                        try {
                            String main = new Gson().toJson(response.body());
                            JSONObject fi = new JSONObject(main);
                            JSONArray reson2 = fi.getJSONArray("data");
                            // userList.clear();

                            JSONArray reson = reson2.getJSONObject(pos).getJSONArray("images");
                            //for position of api start from 0
                            for(int i=0;i<reson.length();i++)
                            {
                                JSONObject jc=reson.getJSONObject(i);
                                imagepojo u = new imagepojo();

                                u.setName(jc.getString("name"));
                                // u.setOriginal(jc.getString("original"));

                                u.setOriginal(jc.getString("original"));
                                imgsList.add(u);

                            }
                            adapter.notifyDataSetChanged();
                        }catch (JSONException e)
                        {
                            e.printStackTrace ();
                        }

                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                  //  Toast.makeText(selectedCategory.this,"error"+t,Toast.LENGTH_SHORT).show();
                    Log.d("err",call.toString());
                }
            });

            return null;
        }
    }
}