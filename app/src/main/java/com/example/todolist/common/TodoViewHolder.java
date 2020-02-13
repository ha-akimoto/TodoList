package com.example.todolist.common;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;

public class TodoViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;
    private CheckBox checkBox;
    private TextView dateView;
    private ImageButton deleteButton;

    public TodoViewHolder(@NonNull View itemView) {
        super(itemView);
        this.textView = itemView.findViewById(R.id.textView_title);
        this.checkBox = itemView.findViewById(R.id.checkBox);
        this.dateView = itemView.findViewById(R.id.dateView);
        this.deleteButton = itemView.findViewById(R.id.deleteButton);
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public TextView getDateView() {
        return dateView;
    }

    public void setDateView(TextView dateView) {
        this.dateView = dateView;
    }

    public ImageButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(ImageButton deleteButton) {
        this.deleteButton = deleteButton;
    }
}
