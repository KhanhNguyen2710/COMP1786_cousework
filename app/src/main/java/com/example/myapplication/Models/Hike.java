package com.example.myapplication.Models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hike_table")
public class Hike {
    @PrimaryKey(autoGenerate = true)
    public long hike_id;
    public long getId() {
        return hike_id;
    }

    public String name;
    public String location;
    public String date;
    public String description;
    public String length;
    public String level;
    public boolean parkingAvailable;


}

