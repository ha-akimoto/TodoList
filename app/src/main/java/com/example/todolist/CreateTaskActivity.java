package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
    }

    public void onClick(View view){

        Intent intent = new Intent();

        //テキストボックスの呼び出し
        EditText etTitle = findViewById(R.id.editText_title);
        //テキストボックスに入力された値を取得して文字列に変換
        String title = etTitle.getText().toString();

        intent.putExtra(Constants.KEY_TITLE, title);
        setResult(Constants.RESULT_CODE,intent);
        finish();
    }
}
