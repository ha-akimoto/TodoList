package com.example.todolist.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.activity.CreateTaskActivity;
import com.example.todolist.adapter.TodoAdapter;
import com.example.todolist.common.Constants;
import com.example.todolist.common.SwipeRemove;
import com.example.todolist.listener.OnRecyclerListener;
import com.example.todolist.room.TodoEntity;
import com.example.todolist.viewmodel.TodoViewModel;

import java.text.SimpleDateFormat;

public class TodoListFragment extends Fragment implements OnRecyclerListener {

    private OnRecyclerListener mListener = (OnRecyclerListener) this;
    private TodoAdapter todoAdapter;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);

    private TodoViewModel todoViewModel;

    public static TodoListFragment newInstance() {
        TodoListFragment fragment = new TodoListFragment();
        return fragment;
    }

    /**
     * フラグメント生成時に呼ばれるメソッド
     *
     * @param savedInstanceState 処理中のインスタンスが保持している値
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * フラグメント内のView生成時に呼ばれるメソッド
     * ローカルDB値の取得とRecyclerViewの作成を行う
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View 作成されたView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        // ViewModelの設定
        this.todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        // アダプターの設定
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        this.todoAdapter = new TodoAdapter(this.mListener, this.todoViewModel);



        recyclerView.setAdapter(this.todoAdapter);

        // 削除処理用コールバックの設定
        ItemTouchHelper itHelper = new ItemTouchHelper(
                new SwipeRemove(0, ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT, this.todoAdapter));

        itHelper.attachToRecyclerView(recyclerView);

        return view;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // リザルトコードが一致する場合
        if (Constants.RESULT_CODE == resultCode && null != data) {

            // Todoタスクを取得
            TodoEntity entity = new TodoEntity();
            entity.title = data.getStringExtra(Constants.KEY_TITLE);
            entity.endDate = data.getStringExtra(Constants.KEY_DATE);
            entity.completeStatus =
                    data.getBooleanExtra(Constants.KEY_COMP_STATUS, false);
            entity.detail = data.getStringExtra(Constants.KEY_DETAIL);

            // リクエストコードの判定
            if (Constants.REQUEST_CODE_INSERT == requestCode) {
                // 追加の場合
                this.todoAdapter.insert(entity);


            } else if (Constants.REQUEST_CODE_UPDATE == requestCode) {
                // 更新の場合
                entity.id = data.getIntExtra(Constants.KEY_ID, 0);
                int position = data.getIntExtra(Constants.KEY_POSITION, -1);
                this.todoAdapter.update(position, entity);
            }
        }

    }

    @Override
    public void onRecyclerClicked(View v, TodoEntity entity, int position) {

        // インテントのインスタンス化
        Intent intent = new Intent(getContext(), CreateTaskActivity.class);

        // Activityに渡す値
        intent.putExtra(Constants.KEY_POSITION, position);
        intent.putExtra(Constants.KEY_ID, entity.id);
        intent.putExtra(Constants.KEY_TITLE, entity.title);
        if (null != entity.endDate) {
            intent.putExtra(Constants.KEY_DATE, entity.endDate);
        }
        intent.putExtra(Constants.KEY_COMP_STATUS, entity.completeStatus);
        intent.putExtra(Constants.KEY_DETAIL, entity.detail);

        // リクエストコードの設定
        int requestCode = Constants.REQUEST_CODE_UPDATE;

        // 返却値を考慮したActivityの起動を行う
        startActivityForResult(intent, requestCode);
    }

    public View createView(View view) {


        return view;
    }
}
