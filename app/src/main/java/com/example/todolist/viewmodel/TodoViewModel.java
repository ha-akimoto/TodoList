package com.example.todolist.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.room.TodoEntity;
import com.example.todolist.room.TodoRepository;

import java.util.List;

public class TodoViewModel extends ViewModel {
    private TodoRepository mRepository;

    //private List<TodoEntity> mLiveTodoEntity;

    public MutableLiveData<List<TodoEntity>> liveTodo = new MutableLiveData<>();

    public TodoViewModel(Context context) {
        //super(application);
        this.mRepository = new TodoRepository(context);
        //this.mLiveTodoEntity = this.mRepository.getLiveTodoEntity();
        this.liveTodo.setValue(this.mRepository.getLiveTodoEntity());
    }
    /*
    public List<TodoEntity> getLiveTodoEntity() {
        return this.mLiveTodoEntity;
    }

     */

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

