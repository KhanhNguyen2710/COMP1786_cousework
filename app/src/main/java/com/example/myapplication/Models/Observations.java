package com.example.myapplication.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "hike_data_table",
        foreignKeys = @ForeignKey(entity = Hike.class,
                parentColumns = "hike_id",
                childColumns = "hike_id",
                onDelete = ForeignKey.CASCADE))
public class Observations {
    @PrimaryKey(autoGenerate = true)
    public long hikeDataId;

    public long hike_id; // Liên kết với hike_id của bảng Hike

    // Dữ liệu bổ sung cho mỗi hike_id
    public String additionalData;
    // ...

    // Các phương thức getter và setter
}