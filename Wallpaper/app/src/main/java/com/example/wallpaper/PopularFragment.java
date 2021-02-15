package com.example.wallpaper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wallpaper.adapters.ImgAdapter;
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


public class PopularFragment extends Fragment {

    private RecyclerView recyclerView;
    private ImgAdapter adapter;
    ArrayList<imagepojo> imgsList=new ArrayList<>();
    String keys="2e29ac80ed9347b199364516639867815653f2";

    public PopularFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular2,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView =view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ImgAdapter(getActivity(), imgsList, new ImgAdapter.ClickHandler() {
            @Override
            public void onButtonClick(int position) {
                imagepojo item = imgsList.get(position);
                Intent intent = new Intent(getActivity(), selectitem.class);
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

//                    Toast.makeText(getActivity(),response.body().toString(),Toast.LENGTH_SHORT).show();
//
//                    Log.e("TAG", "onResponse: "+response.body() );

                    if(response.isSuccessful())
                    {
                        Log.d("in success" , response.toString());
                        try {
                            String main = new Gson().toJson(response.body());
                            JSONObject fi = new JSONObject(main);
                            JSONArray reson2 = fi.getJSONArray("data");
                            // userList.clear();

                            JSONArray reson = reson2.getJSONObject(1).getJSONArray("images");
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
                    Toast.makeText(getActivity(),"error"+t,Toast.LENGTH_SHORT).show();
                    Log.d("err",call.toString());
                }
            });

            return null;
        }
    }
}