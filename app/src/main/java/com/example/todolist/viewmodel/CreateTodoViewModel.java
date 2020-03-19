package com.example.todolist.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateTodoViewModel extends ViewModel {

    private MutableLiveData<String> title;

    private MutableLiveData<String> date;

    private MutableLiveData<String> detail;

    public CreateTodoViewModel(String title, String date, String detail) {
            this.title = new MutableLiveData<>();
            this.title.setValue(title);
            this.date = new MutableLiveData<>();
            this.date.setValue(date);
            this.detail = new MutableLiveData<>();
            this.detail.setValue(detail);
    }

    public MutableLiveData<String> getTitle() {
        return title;
    }

    public void setTitle(MutableLiveData<String> title) {
        this.title = title;
    }

    public MutableLiveData<String> getDate() {
        return date;
    }

    public void setDate(MutableLiveData<String> date) {
        this.date = date;
    }

    public MutableLiveData<String> getDetail() {
        return detail;
    }

    public void setDetail(MutableLiveData<String> detail) {
        this.detail = detail;
    }
}
