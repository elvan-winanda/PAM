package net.elvanwinanda.movieapps.Api;

import net.elvanwinanda.movieapps.Model.MovieProcess;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieInt {

    @GET("movie/popular")
    Call<MovieProcess> reposMovieList(@Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieProcess> reposSearch(@Query("api_key") String apiKey, @Query("query") String movies);
}