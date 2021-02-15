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
import com.example.wallpaper.models.imagepojo;

import java.util.ArrayList;

public class SelectedImgAdapter extends RecyclerView.Adapter<SelectedImgAdapter.UsersViewHolder> {

    private Context mCtx;
    private ArrayList<imagepojo> imgList;
    private ImgAdapter.ClickHandler clickHandler;

    public SelectedImgAdapter(Context mCtx, ArrayList<imagepojo> imgList, ImgAdapter.ClickHandler clickHandler) {
        this.mCtx = mCtx;
        this.imgList = imgList;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.selecte_dategory_recyclerview,parent,false);
        return  new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        holder.clickHandler = this.clickHandler;
        imagepojo image=imgList.get(position);
        holder.textviewsname.setText(image.getName());
        Log.d("url logg",image.getOriginal());
        Glide.with(mCtx).load(image.getOriginal()).into(holder.imgtext);
    }

    @Override
    public int getItemCount() {
        return  imgList.size();
    }

    public interface ClickHandler {
        void onButtonClick(final int position);
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {
        private ImgAdapter.ClickHandler clickHandler;
        TextView textviewsname;
        ImageView imgtext;
        RelativeLayout rlclick;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
             textviewsname=itemView.findViewById(R.id.textviewimage3);
            imgtext=itemView.findViewById(R.id.textviewimage3);
            rlclick = itemView.findViewById(R.id.rlclick3);
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
