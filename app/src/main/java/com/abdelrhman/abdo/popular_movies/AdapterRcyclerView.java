package com.abdelrhman.abdo.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterRcyclerView extends RecyclerView.Adapter<AdapterRcyclerView.Holder>{


   static List<Movie>imagesMivies =new ArrayList<Movie>();
       Context context;
    final public RecyclerViewClickListener mRecyclerViewClickListener;



    public AdapterRcyclerView(List<Movie> movielist,Context mcontext,RecyclerViewClickListener recyclerViewclickListener) {

        imagesMivies=  movielist;
        this.context=mcontext;
        this.mRecyclerViewClickListener =recyclerViewclickListener;

    }



    public interface RecyclerViewClickListener{

        void ViewClick(int postion);
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
       Holder holder = new Holder(view);
       return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

          Movie movie = (imagesMivies.get(position));


            Picasso.with(context)
                    .load(movie.getPosterImage())
                    .into(holder.mImageView);


    }

    @Override
    public int getItemCount() {
        return imagesMivies.size();
    }


    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImageView;


        public Holder(View itemView) {
            super(itemView);

           mImageView = (ImageView)itemView.findViewById(R.id.imageView);
           itemView.setOnClickListener(this);




        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            mRecyclerViewClickListener.ViewClick(position);

        }
    }

    }







