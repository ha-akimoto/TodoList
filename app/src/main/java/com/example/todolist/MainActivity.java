package com.example.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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
     * @param v ビュー
     */
    @Override
    public void onClick(View v) {

        // インテントのインスタンス化（MainActivity）
        Intent intent = new Intent(this,CreateTaskActivity.class);

        // アクティビティの実行
        startActivity(intent);

    }


/*
    @Override
    public void onRecyclerClicked(View v, int position) {

        // インテントのインスタンス化
        Intent intent = new Intent(this,CreateTaskActivity.class);

        // 遷移先から返却されてくる際の識別コード
        int requestCode = Constants.REQUEST_CODE;

        // 返却値を考慮したActivityの起動を行う
        startActivityForResult( intent, requestCode );



    }


 */



    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // startActivityForResult()の際に指定した識別コードとの比較
        if( requestCode == Constants.REQUEST_CODE ) {

            // 返却結果ステータスとの比較
            if (resultCode == Constants.RESULT_CODE) {

                // 返却されてきたintentから値を取り出す
                String str = data.getStringExtra(Constants.KEY_TITLE);
                Bundle args = new Bundle();
                args.putString(Constants.KEY_TITLE,str);

                //onSaveInstanceState(args);

                TodoRow row = new TodoRow();
                row.setTitle(str);
                this.todoListFragment.insertToRecyclerView(row);

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    */

}
