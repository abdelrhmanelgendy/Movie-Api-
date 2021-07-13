package com.example.movie;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.movie.model.Movie_datatable;
import com.example.movie.model.Result;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.movie.MainActivity.movie_datatable;


public class AdapterRec extends RecyclerView.Adapter<AdapterRec.ViewHolder> {
    ArrayList<Result> resultArrayList;
    Context context;
    ArrayList<String> ar = new ArrayList<>();

    List<Result> returnedList;

    private OnItemClickListener mListener;


    public AdapterRec(ArrayList<Result> resultArrayList, Context context) {
        this.resultArrayList = resultArrayList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.custom_recycler_view_layout_, null, false);
        ViewHolder viewHolder = new ViewHolder(v, mListener);

        return viewHolder;
    }

    int changingTag;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        initialize(holder, position);
//        changingTag = tag;
        AsyncTask<Void, Void, List<Result>> execute = new getmovies().execute();
        List<Result> list = null;

        try {
            list = execute.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < list.size(); i++) {
            int x = Integer.parseInt(resultArrayList.get(position).getId() + "");
            int y = Integer.parseInt(list.get(i).getId() + "");
            if (x == y) {
                holder.Movie_favorite.setImageResource(R.drawable.ic_filld_heart);
                changingTag = 1;
            }


        }


        //  Toast.makeText(context, ""+((resultArrayList.get(0).getId())-(list.get(0).getId())), Toast.LENGTH_SHORT).show();


        String baseUrl = "http://image.tmdb.org/t/p/w500";
        String tittle = resultArrayList.get(position).getTitle();
        String date = resultArrayList.get(position).getReleaseDate();
        String rate = resultArrayList.get(position).getVoteAverage() + "";
        String overview = resultArrayList.get(position).getOverview();
        String poster_path = baseUrl + resultArrayList.get(position).getPosterPath();
        String backdrop = baseUrl + "" + resultArrayList.get(position).getBackdropPath();

        holder.Movie_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (changingTag == 1) {
                        delete(resultArrayList.get(position), holder);
                        holder.Movie_favorite.setImageResource(R.drawable.ic_empty_heart);
                        changingTag = 2;

                    } else {

                        addMovie(resultArrayList.get(position), holder);
                        holder.Movie_favorite.setImageResource(R.drawable.ic_filld_heart);
                        changingTag = 1;
                    }
                } catch (Exception e) {
                    return;
                }
            }
        });


        holder.MoviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    private void delete(Result result, ViewHolder holder) {


        holder.Movie_favorite.setImageResource(R.drawable.ic_filld_heart);

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

    private void addMovie(Result result, ViewHolder holder) {


        holder.Movie_favorite.setImageResource(R.drawable.ic_filld_heart);

        new insert().execute(result);

    }

    class insert extends AsyncTask<Result, Void, Void> {


        @Override
        protected Void doInBackground(Result... results) {
            try {


                MovieDao movieDao = movie_datatable.getmovie();
                movieDao.insert(results[0]);
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //  Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();
        }
    }


    private void initialize(ViewHolder holder, int position) {
        String baseUrl = "http://image.tmdb.org/t/p/w500";
        String imageUrl = resultArrayList.get(position).getPosterPath();
        holder.txtMovieName.setText(resultArrayList.get(position).getTitle() + "");
        holder.txtMovieDate.setText(resultArrayList.get(position).getReleaseDate() + "");
        holder.txtMovieRate.setText(resultArrayList.get(position).getVoteAverage() + "");


//        int id = R.drawable.ic_empty_heart;
//        holder.Movie_favorite.setImageResource(id);
//        holder.Movie_favorite.setTag(id);

        Picasso.get().load(baseUrl + imageUrl).into(holder.MoviePoster);
//        return id;
    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMovieName;
        TextView txtMovieDate;
        TextView txtMovieRate;

        ImageView MoviePoster;
        ImageView Movie_favorite;
        ImageView Movie_rates;


        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);


            txtMovieName = itemView.findViewById(R.id.txtname);
            txtMovieDate = itemView.findViewById(R.id.txtdate);
            txtMovieRate = itemView.findViewById(R.id.txtrate);
            MoviePoster = itemView.findViewById(R.id.custome_layout_posterImage);
            Movie_favorite = itemView.findViewById(R.id.custome_layout_imageLike_dislike);
            Movie_rates = itemView.findViewById(R.id.imageView2);


        }
    }

    class getmovies extends AsyncTask<Void, Void, List<Result>> {


        @Override
        protected List<Result> doInBackground(Void... voids) {

            MovieDao movieDao = movie_datatable.getmovie();
            List<Result> getmovies = movieDao.getmovies();


            return getmovies;
        }

        @Override
        protected void onPostExecute(List<Result> results) {
            super.onPostExecute(results);
            //     Toast.makeText(context, ""+results.size(), Toast.LENGTH_SHORT).show();

        }
    }

}
