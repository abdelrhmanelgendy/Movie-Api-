package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.movie.model.Movie_datatable;
import com.example.movie.model.Result;

import java.util.List;

import static com.example.movie.MainActivity.movie_datatable;

public class FavoriteActivity extends AppCompatActivity {

movie_adapter movie_adapter ;
RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        new getmovies().execute();
recyclerView=findViewById(R.id.recMovie);








    }


    class getmovies extends AsyncTask<Void,Void, List<Result>>
    {


        @Override
        protected List<Result> doInBackground(Void... voids) {

MovieDao movieDao = movie_datatable.getmovie();
            List<Result> getmovies = movieDao.getmovies();


            return getmovies;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            super.onPostExecute(results);

            RecyclerView.LayoutManager linear = new GridLayoutManager(getApplicationContext(),1);
            DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getApplicationContext(),GridLayoutManager.VERTICAL);
            movie_adapter =new movie_adapter(results,getApplicationContext());

            //recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setLayoutManager(linear);
            recyclerView.setAdapter(movie_adapter);
        }
    }
}
