package com.example.myapplication.dao;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Models.Hike;

import java.util.List;

@Dao
public interface HikeDao {
    @Insert
    long insertHike(Hike hike);

    @Query("SELECT * FROM hike_table ORDER BY name")
    List<Hike> getAllHike();


    @Query("SELECT * FROM hike_table WHERE hike_id = :hikeId")
    Hike getHikeById(long hikeId);

    @Delete
    void deleteHike(Hike hike);

    @Query("DELETE FROM hike_table")
    void deleteAllHikes();

    @Update
    void updateHike(Hike hike);

    @Query("SELECT * FROM hike_table WHERE name LIKE :searchName")
    List<Hike> searchHikesByName(String searchName);
}
