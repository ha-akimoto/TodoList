package com.example.todolist.listener;

import android.view.View;

import com.example.todolist.model.TodoRow;

public interface OnRecyclerListener {

    void onRecyclerClicked(View v, TodoRow row);

}
