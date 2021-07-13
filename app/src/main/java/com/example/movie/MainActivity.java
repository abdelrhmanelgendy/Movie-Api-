package com.example.movie;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import com.example.movie.Databases.remote.MovieAPi;
import com.example.movie.model.Movie;
import com.example.movie.model.Movie_datatable;
import com.example.movie.model.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
ArrayList<Result> myarray=new ArrayList<>();
RecyclerView recyclerView;
    AdapterRec adapter;
  public static   Movie_datatable movie_datatable ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        movie_datatable = Room.databaseBuilder(this, Movie_datatable.class, "movie_database").build();
        recyclerView=findViewById(R.id.recycler);
getmovielist();



    }

    public  void getmovielist() {

        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieAPi movieAPi=retrofit.create(MovieAPi.class);

        Call<Movie> call =movieAPi.getresult("d032214048c9ca94d788dcf68434f385");

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                myarray= (ArrayList<Result>) response.body().getResults();


                 adapter = new AdapterRec(myarray, getApplicationContext());
                GridLayoutManager layoutManager =new GridLayoutManager(getApplicationContext(),2);
                DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getApplicationContext(),GridLayoutManager.VERTICAL);
//recyclerView.addItemDecoration(dividerItemDecoration);
recyclerView.setLayoutManager(layoutManager);
recyclerView.setAdapter(adapter);




            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.favorite,menu);
        return true;


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent(MainActivity.this,FavoriteActivity.class);
                startActivity(intent);
                 return true;
    }


    class getmoviess extends AsyncTask<Void, Void, List<Result>> {


        @Override
        protected List<Result> doInBackground(Void... voids) {

            MovieDao movieDao = movie_datatable.getmovie();
            List<Result> getmovies = movieDao.getmovies();


            return getmovies;
        }}
}
