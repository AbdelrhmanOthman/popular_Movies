package com.abdelrhman.abdo.popular_movies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExtractData {


// الدالة المجمعة
      public static List<Movie> getDataFromUrl(String urlRequest){


          String JasonResponse = null;

          try {
              URL url = CreatURL(urlRequest);

              JasonResponse=makeHttpRequest(url);
          } catch (Exception e) {
              e.printStackTrace();
          }

         List<Movie> movieDetail = EXtractDataFromJason(JasonResponse);
          return movieDetail;
      }


      // انشاء ال الرابط
    private static URL CreatURL (String stringUrl){

        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    return url;
    }



    // انشاء الاتصال

    private static String makeHttpRequest(URL url)throws Exception{
        String JasonResponse = null;

        if (url==null){
            return JasonResponse;
        }


        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

         try {


             httpURLConnection = (HttpURLConnection) url.openConnection();

             httpURLConnection.setRequestMethod("GET");
             httpURLConnection.connect();

             if (httpURLConnection.getResponseCode() == 200) {

                 inputStream = httpURLConnection.getInputStream();

                 JasonResponse = readFromINputStream(inputStream);

             } else {

                 Log.e("Faild to connect", "Error response code: " + httpURLConnection.getResponseCode());


             }
         }catch (IOException e){


         }
         finally {
             if (httpURLConnection != null) {
                 httpURLConnection.disconnect();
             }

         }
        return JasonResponse;
    }


   private static String readFromINputStream(InputStream inputStream)throws Exception{

        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream !=null){


            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);


            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();

            while (line!=null){
                stringBuilder.append(line);

                line=bufferedReader.readLine();
            }
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (final IOException e) {
                    Log.e("ERROR", "Error closing stream", e);
                }
            }
        }

        return stringBuilder.toString();


   }




   private static ArrayList<Movie> EXtractDataFromJason(String movieDetail){


          ArrayList<Movie> mlist = new ArrayList<>();


       try {
           JSONObject MoviesData = new JSONObject(movieDetail);

           JSONArray resultArray = MoviesData.getJSONArray("results");

           for (int i = 0 ;i<resultArray.length();i++){


               JSONObject firstMovieData = resultArray.getJSONObject(i);

               Double userRating = firstMovieData.getDouble("vote_average");

               String originalTitle = firstMovieData.getString("original_title");

               String backDropPathe = firstMovieData.getString("backdrop_path");
               String baseUrl = "http://image.tmdb.org/t/p/";
               String Size = "w185";
               String moviePoster =baseUrl+Size+backDropPathe;


               String overView = firstMovieData.getString("overview");

               String releaseDate = firstMovieData.getString("release_date");


              Movie movies = new Movie(originalTitle,moviePoster,releaseDate,userRating,overView);

                   mlist.add(movies);

           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
       return mlist;
   }
}


