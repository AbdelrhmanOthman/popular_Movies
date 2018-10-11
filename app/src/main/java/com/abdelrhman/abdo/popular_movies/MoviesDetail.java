package com.abdelrhman.abdo.popular_movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesDetail extends AppCompatActivity {

     String baseUrl ="https://api.themoviedb.org/3/movie/";
     String sortType ;
     String apiKey ="?api_key="+"Put your key here";




    TextView Name,Date,Rating,overView;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);
        sortType=MainActivity.mSharedPreferences.getString("sort_type","popular");


        String PopularMoviesUrl =baseUrl+sortType+apiKey;
        Task task = new Task();
        task.execute(PopularMoviesUrl);

        Name = (TextView)findViewById(R.id.name);
        Date = (TextView)findViewById(R.id.date);
       Rating = (TextView)findViewById(R.id.rating);
        overView= (TextView)findViewById(R.id.over_view);
        mImageView = (ImageView)findViewById(R.id.im);



    }

    public class Task extends AsyncTask<String,Void,List<Movie>>{

        @Override
        protected List<Movie> doInBackground(String... strings) {
            if (strings.length<1||strings[0]==null){
                return null;

            }

            List<Movie>movie = ExtractData.getDataFromUrl(strings[0]);

            return movie;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            Intent intent = getIntent();

             int postion = intent.getIntExtra("Postion",-1);


                Name.setText("Name"+"\n\n"+movies.get(postion).getOriginalTitle());
                Date.setText("Relese Date"+"\n\n"+movies.get(postion).getReleaseDate());
                overView.setText(movies.get(postion).getOverView());
                Rating.setText("Rating"+"\n\n"+movies.get(postion).getUserRating().toString());

                Picasso.with(getApplicationContext())
                        .load(movies.get(postion).getPosterImage())
                        .into(mImageView);


        }
    }







    }



