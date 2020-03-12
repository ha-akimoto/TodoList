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


    public String getTitle() {
        return title.getValue();
    }

    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public String getDate() {
        return date.getValue();
    }

    public void setDate(String date) {
        this.date.setValue(date);
    }

    public String getDetail() {
        return detail.getValue();
    }

    public void setDetail(String detail) {
        this.detail.setValue(detail);
    }
}
