package com.example.todolist.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {

    private TodoDao mTodoDao;

    private LiveData<List<TodoEntity>> mLiveTodoEntity;


    public TodoRepository(Application application) {
        TodoDB db = TodoDB.getDatabase(application);
        this.mTodoDao = db.todoDao();
        this.mLiveTodoEntity = this.mTodoDao.getAllSorted();

    }

    public LiveData<List<TodoEntity>> getLiveTodoEntity() {
        return this.mLiveTodoEntity;
    }

    public LiveData<List<TodoEntity>> getAllSorted() {
        return this.mTodoDao.getAllSorted();
    }

    public void insert(TodoEntity todoEntity) {
        TodoDB.databaseWriteExecutor.execute(() -> {
            this.mTodoDao.insertAll(todoEntity);
        });
    }

    public void update(TodoEntity todoEntity) {
        TodoDB.databaseWriteExecutor.execute(() -> {
            this.mTodoDao.update(todoEntity);
        });
    }

    public void delete(TodoEntity todoEntity) {
        TodoDB.databaseWriteExecutor.execute(() -> {
            this.mTodoDao.delete(todoEntity);
        });
    }

    public int getMaxId() {
        return this.mTodoDao.getMaxId();
    }
}
