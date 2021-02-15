package com.example.wallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.wallpaper.adapters.CategoryAdapter;
import com.example.wallpaper.adapters.ImgAdapter;
import com.example.wallpaper.api.RetrofitClient;
import com.example.wallpaper.models.categorypojo;
import com.example.wallpaper.models.imagepojo;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    ArrayList<categorypojo> catList=new ArrayList<>();
    String keys="2e29ac80ed9347b199364516639867815653f2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerView =findViewById(R.id.recyclerView1);
       // RecyclerView.LayoutManager layoutManager = new GridLayoutManager(CategoryActivity.this,2);
      //  recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
        Log.d("catlist size", String.valueOf(catList.size()));
//                            adapter.notifyDataSetChanged();
        adapter= new CategoryAdapter(CategoryActivity.this, catList, new CategoryAdapter.ClickHandler() {
            @Override
            public void onButtonClick(int position) {
                categorypojo item = catList.get(position);
                Intent intent = new Intent(CategoryActivity.this, selectedCategory.class);
                intent.putExtra("CatenameI",item.getCategory());
                intent.putExtra("CateimageI",item.getCategory_image());
                intent.putExtra("position" ,position); //for position for category
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

//                    Toast.makeText(getActivity(),response.body().toString(),Toast.LENGTH_SHORT).show();
//
//                    Log.e("TAG", "onResponse: "+response.body() );

                    Log.e("TAG", "onResponse: "+response.body() );
                    if(response.isSuccessful())
                    {
                        Log.d("in success" , response.toString());
                        try {
                            String main = new Gson().toJson(response.body());
                            JSONObject fi = new JSONObject(main);
                            JSONArray reson2 = fi.getJSONArray("data");
                            // userList.clear();

                            // JSONArray reson = reson2.getJSONObject(0).getJSONArray("images");
                            //for position of api start from 0
                            for(int i=0;i<reson2.length();i++)
                            {
                                JSONObject jc=reson2.getJSONObject(i);
                                categorypojo u = new categorypojo();
                                u.setCategory(jc.getString("category"));
                                u.setCategory_image(jc.getString("category_image"));
//                                u.setName(jc.getString("name"));
//                                // u.setOriginal(jc.getString("original"));
//
//                                u.setOriginal(jc.getString("original"));
                                catList.add(u);

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
                    Toast.makeText(CategoryActivity.this,"error"+t,Toast.LENGTH_SHORT).show();
                    Log.d("err",call.toString());
                }
            });

            return null;
        }
    }

}