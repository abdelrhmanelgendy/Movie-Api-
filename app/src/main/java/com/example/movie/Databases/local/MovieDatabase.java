package com.example.movie.Databases.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.movie.MovieDao;
import com.example.movie.model.Result;

@Database(entities = Result.class,version = 1)
public  abstract class MovieDatabase extends RoomDatabase {
public abstract MovieDao getmovie();

}
