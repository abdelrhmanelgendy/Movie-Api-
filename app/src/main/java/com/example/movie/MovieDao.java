package com.example.movie;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movie.model.Result;

import java.util.List;

@Dao
public interface MovieDao {

@Insert
void insert(Result result);

@Query("select * from movie_table")
    List<Result> getmovies();


@Delete
    void delete(Result result);

}
