package com.abdelrhman.abdo.popular_movies;

public class Movie {

    public  String originalTitle;
    public  String posterImage;
    public  String releaseDate;
    public  Double userRating;
    public  String overView;





    public Movie(){

    }

    public Movie(String originalTitle, String posterImage, String releaseDate, Double userRating, String overView) {
        this.originalTitle = originalTitle;
        this.posterImage = posterImage;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.overView = overView;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }
}
