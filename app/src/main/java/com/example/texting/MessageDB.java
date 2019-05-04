package com.example.texting;

import android.content.Context;

import java.util.List;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
interface DataObj{
    @Query("SELECT * FROM `Message`")
    List<Message> selectAll();

    @Insert
    void insert(Message message);

    @Delete
    void delete(Message message);
}

@Database(entities = {Message.class}, version = 1, exportSchema = false)
public abstract class MessageDB extends RoomDatabase {
    private static MessageDB singleton = null ;

    // Logic to keep the DB as a singleton with every app launch
    public static MessageDB getInstance(Context context){
        if( MessageDB.singleton == null ){
            MessageDB.singleton = Room.databaseBuilder(context.getApplicationContext(), MessageDB.class, "user-db")
                    .allowMainThreadQueries().build();
        }
        return MessageDB.singleton;
    }

    // This data object will hold the logic for accessing message data
    public abstract DataObj dataObj();

}
