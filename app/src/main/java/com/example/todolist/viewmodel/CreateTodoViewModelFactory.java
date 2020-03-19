package com.example.todolist.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CreateTodoViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private String title;
    private String date;
    private String detail;

    public CreateTodoViewModelFactory(@NonNull Application application,
                                      String title, String date, String detail) {
        super(application);
        this.title = title;
        this.date = date;
        this.detail = detail;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == CreateTodoViewModel.class) {
            return (T) new CreateTodoViewModel(this.title, this.date, this.detail);
        }
        return super.create(modelClass);
    }
}
