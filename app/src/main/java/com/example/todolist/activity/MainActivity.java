package com.example.todolist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.common.Constants;
import com.example.todolist.fragment.TodoListFragment;
import com.example.todolist.viewmodel.TodoRow;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView todoListView;
    private ArrayList<TodoRow> todoList = new ArrayList<TodoRow>();
    private TodoListFragment todoListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // リスナーの設定
        findViewById(R.id.button_createTodo).setOnClickListener(this);

        // フラグメントの設定
        FragmentManager fragmentManager = getSupportFragmentManager();
        this.todoListFragment = (TodoListFragment) fragmentManager.findFragmentById(R.id.fragment_todoList);

    }

    /**
     * Todo追加ボタン押下
     *
     * @param v ビュー
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CreateTaskActivity.class);

        // リクエストコード
        int requestCode = Constants.REQUEST_CODE_INSERT;

        // リクエストコード有りのActivityの起動を行う
        startActivityForResult(intent, requestCode);

    }


    /**
     * 返却値有りのActivityが終了した際に呼び出されるメソッド
     * フラグメントにある同じメソッドを呼び出す
     *
     * @param requestCode リクエストコード
     * @param resultCode  リザルトコード
     * @param data        返却される値
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.todoListFragment.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}
