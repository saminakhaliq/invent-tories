package com.example.bilhaghedeon.finalprojectghedeonkhaliq.dB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.bilhaghedeon.finalprojectghedeonkhaliq.model.Item;

/**
 * Implemented by Samina Khaliq
 */
@Database(entities = {Item.class}, version = 1,exportSchema = false)
public abstract class ItemsRoomDatabase extends RoomDatabase {
    private static ItemsRoomDatabase INSTANCE;

    public abstract ItemDao itemDao();

    public static ItemsRoomDatabase getDatabase(Context context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, ItemsRoomDatabase.class,
                    "item_db").build();

        }
        return INSTANCE;

    }
}
