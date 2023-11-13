package com.example.myapplication.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.Models.Hike;
import com.example.myapplication.dao.HikeDao;

@Database(entities = {Hike.class}, version = 1)
public abstract class HikeDatabase extends RoomDatabase {
    public abstract HikeDao hikeDao();
}