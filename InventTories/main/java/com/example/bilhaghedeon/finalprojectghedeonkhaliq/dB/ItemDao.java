package com.example.bilhaghedeon.finalprojectghedeonkhaliq.dB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.bilhaghedeon.finalprojectghedeonkhaliq.model.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    long insert(Item item);

    @Query("SELECT * FROM items_table ORDER BY UPPER(name)")
    List<Item> getAllItems();

    @Query("SELECT * FROM items_table ORDER BY quantity")
    List<Item> getAllQuantities();


    @Query("DELETE FROM items_table WHERE name = :name")
    int deleteItemByName(String name);

    @Delete
    int delete(Item item);
}
