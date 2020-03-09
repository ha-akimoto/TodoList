package com.example.todolist.room;

import android.content.Context;

import java.util.List;

public class TodoRepository {

    private TodoDao mTodoDao;

    private List<TodoEntity> mLiveTodoEntity;


    public TodoRepository(Context context) {
        TodoDB db = TodoDB.getDatabase(context);
        this.mTodoDao = db.todoDao();
        this.mLiveTodoEntity = this.mTodoDao.getAllSorted();

    }

    public List<TodoEntity> getLiveTodoEntity() {
        return this.mLiveTodoEntity;
    }

    public List<TodoEntity> getAllSorted() {
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
