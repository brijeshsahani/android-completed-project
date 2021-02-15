package com.example.wallpaper.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wallpaper.R;
import com.example.wallpaper.models.categorypojo;
import com.example.wallpaper.models.imagepojo;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.UsersViewHolder> {
    private Context mCtx;
    private ArrayList<categorypojo> imgList;
    private ClickHandler clickHandler;

    public CategoryAdapter(Context mCtx, ArrayList<categorypojo> imgList, ClickHandler clickHandler) {
        this.mCtx = mCtx;
        this.imgList = imgList;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public CategoryAdapter.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.categotyrecyclerview,parent,false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.UsersViewHolder holder, int position) {
        holder.clickHandler = this.clickHandler;
        categorypojo catee=imgList.get(position);
        holder.textviewsname.setText(catee.getCategory());
        Log.d("url logg",catee.getCategory_image());
        Glide.with(mCtx).load(catee.getCategory_image()).into(holder.imgtext);
    }

    @Override
    public int getItemCount() {
        return  imgList.size();
    }

    public interface ClickHandler {
        void onButtonClick(final int position);
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {
        private CategoryAdapter.ClickHandler clickHandler;
        TextView textviewsname;
        ImageView imgtext;
        RelativeLayout rlclick;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            textviewsname=itemView.findViewById(R.id.textviewname1);
            imgtext=itemView.findViewById(R.id.textviewimage1);
            rlclick = itemView.findViewById(R.id.rlclick1);
            rlclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickHandler != null){
                        clickHandler.onButtonClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
