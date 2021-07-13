package com.example.movie.Databases.remote;





import com.example.movie.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieAPi {


    @GET("popular")
    public Call<Movie> getresult(@Query("api_key") String api_key);
}
