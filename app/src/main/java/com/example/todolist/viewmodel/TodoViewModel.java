package com.example.todolist.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todolist.room.TodoEntity;
import com.example.todolist.room.TodoRepository;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private TodoRepository mRepository;

    private LiveData<List<TodoEntity>> mLiveTodoEntity;

    public TodoViewModel(Application application) {
        super(application);
        this.mRepository = new TodoRepository(application);
        this.mLiveTodoEntity = this.mRepository.getLiveTodoEntity();
    }

    public LiveData<List<TodoEntity>> getLiveTodoEntity() {
        return this.mLiveTodoEntity;
    }

    public void insert(TodoEntity todoEntity) {
        this.mRepository.insert(todoEntity);
    }

    public void update(TodoEntity todoEntity) {
        this.mRepository.update(todoEntity);
    }

    public void delete(TodoEntity todoEntity) {
        this.mRepository.delete(todoEntity);
    }

    public int getMaxId() {
        return this.mRepository.getMaxId();
    }
}

