package com.example.todolist.viewmodel;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


public class TodoRow extends BaseObservable {

    @Bindable
    private int id;

    @Bindable
    private String title;

    @Bindable
    private String endDate;

    @Bindable
    private String detail;

    @Bindable
    private boolean completeStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(com.example.todolist.BR.id);
    }

    public String getTitle() {
        return title;

    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(com.example.todolist.BR.title);
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
        notifyPropertyChanged(com.example.todolist.BR.endDate);
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
        notifyPropertyChanged(com.example.todolist.BR.detail);
    }

    public boolean getCompleteStatus() {
        return completeStatus;
    }

    public void setCompleteStatus(boolean completeStatus) {
        this.completeStatus = completeStatus;
        notifyPropertyChanged(com.example.todolist.BR.completeStatus);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof TodoRow) {
            TodoRow objRow = (TodoRow) obj;
            if (this.id == objRow.getId()
                    && this.detail.equals(objRow.getDetail())
                    && this.completeStatus == objRow.getCompleteStatus()
                    && this.endDate.equals(objRow.getEndDate())
                    && this.title.equals(objRow.getTitle())) {
                return true;
            } else {
                return false;
            }
        } else {
            return super.equals(obj);
        }
    }

}
