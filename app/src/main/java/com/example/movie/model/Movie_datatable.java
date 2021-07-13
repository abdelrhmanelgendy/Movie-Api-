package com.example.movie.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.movie.MovieDao;

@Database(entities = Result.class,version = 1)
public abstract class Movie_datatable extends RoomDatabase {
public abstract MovieDao getmovie();


}
