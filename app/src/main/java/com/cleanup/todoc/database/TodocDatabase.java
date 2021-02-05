package com.cleanup.todoc.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase{

    // Singleton
    private static volatile TodocDatabase INSTANCE;

    // DAO
    public abstract TaskDao taskDao();

    // Instance
    public static TodocDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (TodocDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "Task")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static Callback prepopulateDatabase(){
        return new Callback(){

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db){
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("projectId", 1);
                contentValues.put("name", "Initial task");
                contentValues.put("creationTimestamp", 1);

                db.insert("Task", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}
