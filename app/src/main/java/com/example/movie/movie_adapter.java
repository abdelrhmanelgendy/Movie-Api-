package com.example.movie;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.movie.MainActivity.movie_datatable;

public class movie_adapter extends RecyclerView.Adapter<movie_adapter.holder> {

    List<Result> mylist;
    Context context;
    private String baseUrl;
    private String tittle;
    private String overview;
    private String date;
    private String rate;
    private String poster_path;
    private String backdrop;


    public movie_adapter(List<Result> mmylist, Context context) {
        this.mylist = mmylist;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.custome_movie, null);
        holder mholder = new holder(v);
        return mholder;
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {


        holder.txtMovieName.setText(mylist.get(position).getTitle());
        holder.txtMovieDate.setText(mylist.get(position).getReleaseDate());
        holder.likedImage.setImageResource(R.drawable.ic_filld_heart);

        Picasso.get().load("http://image.tmdb.org/t/p/w500" + mylist.get(position).getPosterPath()).into(holder.posterImage);

        holder.likedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//Result result =mylist.get(position);
//
//
//        deleteMovie(result,holder);
//        mylist.remove(position);
//        notifyDataSetChanged();
            }
        });


        holder.posterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseUrl = "http://image.tmdb.org/t/p/w500";
                tittle = mylist.get(position).getTitle();
                date = mylist.get(position).getReleaseDate();
                rate = mylist.get(position).getVoteAverage() + "";
                overview = mylist.get(position).getOverview();
                poster_path = baseUrl + mylist.get(position).getPosterPath();
                backdrop = baseUrl + "" + mylist.get(position).getBackdropPath();
                Intent intent = new Intent(view.getContext(), secondActivity.class);
                intent.putExtra("tittle", tittle);
                intent.putExtra("date", date);
                intent.putExtra("rate", rate);
                intent.putExtra("overview", overview);
                intent.putExtra("poster_path", poster_path);
                intent.putExtra("backdrop", backdrop);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });


    }

    private void deleteMovie(Result result, RecyclerView.ViewHolder holder) {


        new delete().execute(result);
    }

    class delete extends AsyncTask<Result, Void, Void> {


        @Override
        protected Void doInBackground(Result... results) {
            MovieDao movieDao = movie_datatable.getmovie();
            movieDao.delete(results[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    class holder extends RecyclerView.ViewHolder {

        ImageView posterImage;
        TextView txtMovieName;
        TextView txtMovieDate;
        ImageView likedImage;


        public holder(@NonNull View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.custom_movie_poster);
            txtMovieName = itemView.findViewById(R.id.custom_movie_txtname);
            likedImage = itemView.findViewById(R.id.custom_movie_likedimage);
            txtMovieDate = itemView.findViewById(R.id.custom_movie_txtdate);


        }
    }


}
