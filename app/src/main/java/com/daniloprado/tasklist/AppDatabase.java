package com.daniloprado.tasklist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.daniloprado.tasklist.task.Task;
import com.daniloprado.tasklist.task.TaskDao;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    AppDatabase.class,
                    "tasklist.db").allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract TaskDao taskDao();
}
