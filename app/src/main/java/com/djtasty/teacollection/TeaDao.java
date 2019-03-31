package com.djtasty.teacollection;

import android.provider.ContactsContract;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

// Dao for Database

@Dao
public interface TeaDao {

    @Insert
    void insert(Tea tea);

    @Update
    void update(Tea tea);

    @Delete
    void delete(Tea tea);

    @Query("DELETE FROM tea_collection_table")
    void deleteAllTea();

    @Query("SELECT * FROM tea_collection_table ORDER BY quantity DESC")
    LiveData<List<Tea>> getAllTea();
}
