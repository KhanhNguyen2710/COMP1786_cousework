package com.example.myapplication.dao;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.Models.Hike;

import java.util.List;

@Dao
public interface HikeDao {
    @Insert
    long insertHike(Hike hike);

    @Query("SELECT * FROM hike_table ORDER BY name")
    List<Hike> getAllHike();

    @Delete
    void deleteHike(Hike hike);
}
