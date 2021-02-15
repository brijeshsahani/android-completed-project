package com.example.wallpaper.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.wallpaper.models.imagepojo;
import com.example.wallpaper.selectitem;

import java.util.ArrayList;

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.UsersViewHolder> {

    private Context mCtx;
    private ArrayList<imagepojo> imgList;
    private ClickHandler clickHandler;

    public ImgAdapter(Context mCtx, ArrayList<imagepojo> imgList, ClickHandler clickHandler) {
        this.mCtx = mCtx;
        this.imgList = imgList;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ImgAdapter.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview,parent,false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImgAdapter.UsersViewHolder holder, int position) {
        holder.clickHandler = this.clickHandler;
        imagepojo image=imgList.get(position);
     //  holder.textviewsname.setText(image.getName());
        Glide.with(mCtx).load(image.getOriginal()).into(holder.imgtext);

    }

    @Override
    public int getItemCount() {
        return  imgList.size();
    }
    public interface ClickHandler {
        void onButtonClick(final int position);
    }

    class UsersViewHolder extends RecyclerView.ViewHolder{
        private ClickHandler clickHandler;
       TextView textviewsname;
        ImageView imgtext;
        RelativeLayout rlclick;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
          // textviewsname=itemView.findViewById(R.id.textviewname);
            imgtext=itemView.findViewById(R.id.textviewimage);
            rlclick = itemView.findViewById(R.id.rlclick);
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
