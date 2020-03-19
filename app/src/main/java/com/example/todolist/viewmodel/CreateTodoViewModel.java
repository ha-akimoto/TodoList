package com.example.todolist.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateTodoViewModel extends ViewModel {

    private MutableLiveData<String> title;

    private MutableLiveData<String> date;

    private MutableLiveData<String> detail;

    /**
     * 初期化
     * フィールドの値がNullの場合のみ、値をセットする
     *
     * @param title  タイトル
     * @param date   期限日
     * @param detail 詳細
     */
    public void init(String title, String date, String detail) {
        if (this.title == null) {
            this.title = new MutableLiveData<>();
            this.title.setValue(title);
        }
        if (this.date == null) {
            this.date = new MutableLiveData<>();
            this.date.setValue(date);
        }
        if (this.detail == null) {
            this.detail = new MutableLiveData<>();
            this.detail.setValue(detail);
        }
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
