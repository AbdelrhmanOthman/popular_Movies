package com.abdelrhman.abdo.popular_movies;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterRcyclerView.RecyclerViewClickListener{

    RecyclerView mRecyclerView ;

    AdapterRcyclerView mAdapterRcyclerView;

    static SharedPreferences mSharedPreferences;

    String baseUrl ="https://api.themoviedb.org/3/movie/";

     TextView mTextView;

     String sortType;
     String apiKey ="?api_key="+"Put your key here";
    int selected;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSharedPreferences = getSharedPreferences("popularMovie", MODE_PRIVATE);
        sortType = mSharedPreferences.getString("sort_type", "popular");

         mTextView = (TextView)findViewById(R.id.tvt);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(this.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                String PopularMoviesUrl = baseUrl + sortType + apiKey;

                ImageTask imageTask = new ImageTask();

                imageTask.execute(PopularMoviesUrl);


            }else {
                mTextView.setText("Sorry No internt Conection");
                mTextView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id==R.id.sort_settings){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final SharedPreferences.Editor editor = mSharedPreferences.edit();
            selected = 0;
            sortType = mSharedPreferences.getString("sort_type", "popular");
            if(sortType.equals("popular"))
                selected = 0;
            else if(sortType.equals("top_rated"))
                selected = 1;
            builder.setTitle(R.string.DialogTitle);
            builder.setSingleChoiceItems(R.array.Sort_types, selected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i==0){
                        editor.putString("sort_type","popular");
                    }else if (i==1){
                        editor.putString("sort_type","top_rated");
                    }

                }
            });

            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    finish();
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);

                }
            });
            builder.setPositiveButton(R.string.DialogSave, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    editor.commit();

                }
            });
            builder.setNegativeButton(R.string.DialogCancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public void ViewClick(int postion) {

        Intent intent= new Intent(this,MoviesDetail.class);

        intent.putExtra("Postion",postion);
        startActivity(intent);

    }





    public class ImageTask extends AsyncTask<String, Void, List<Movie>> {
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
            mAdapterRcyclerView = new AdapterRcyclerView(movies,getApplicationContext(),MainActivity.this);
            try {


                mRecyclerView.setAdapter(mAdapterRcyclerView);


                mAdapterRcyclerView.notifyDataSetChanged();
            }catch (NullPointerException e){
                Log.d("ERROR", "Error", e);

            }
        }
    }

}
