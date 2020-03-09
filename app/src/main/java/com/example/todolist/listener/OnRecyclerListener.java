package com.example.todolist.listener;

import android.view.View;

import com.example.todolist.room.TodoEntity;

public interface OnRecyclerListener {

    void onRecyclerClicked(View v, TodoEntity entity, int position);

}
