package com.daniloprado.tasklist.task;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.daniloprado.tasklist.AppDatabase;

import java.util.List;

class TaskViewModel extends AndroidViewModel {
    private LiveData<List<Task>> tasks;
    private AppDatabase db;

    public TaskViewModel(Application application) {
        super(application);
        db = AppDatabase.getInstance(this.getApplication().getApplicationContext());
        tasks = db.taskDao().getAll();
    }

    LiveData<List<Task>> getTasks() {
        return tasks;
    }

    void saveTask(Task task) {
        db.taskDao().insert(task);
    }

    void deleteTask(Task task) {
        db.taskDao().delete(task);
    }

}
