package com.example.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *  interface
 * to handle interaction events.
 * Use the {@link TodoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodoListFragment extends Fragment {

    private OnRecyclerListener mListener = new OnRecyclerListener() {
        @Override
        public void onRecyclerClicked(View v, int position) {

            // インテントのインスタンス化
            Intent intent = new Intent(getContext(),CreateTaskActivity.class);

            // 遷移先から返却されてくる際の識別コード
            int requestCode = Constants.REQUEST_CODE;

            // 返却値を考慮したActivityの起動を行う
            startActivityForResult( intent, requestCode );
        }
    };
    private TodoAdapter todoAdapter;
    private List<TodoRow> todoList;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment TodoListFragment.
     */
    public static TodoListFragment newInstance() {
        TodoListFragment fragment = new TodoListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);
        this.todoList = new ArrayList<TodoRow>();

        // デバッグ用初期値
        TodoRow debugTodoRow = new TodoRow();
        debugTodoRow.setTitle("aaaa");
        this.todoList.add(debugTodoRow);
        // バンドルから取る用？
        Bundle args = getArguments();
        if(null != args){
            TodoRow todoRow = new TodoRow();
            todoRow.setTitle(args.getString(Constants.KEY_TITLE));
            this.todoList.add(todoRow);
        }

        // アダプターの設定
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            this.todoAdapter = new TodoAdapter(this.todoList,this.mListener);
            recyclerView.setAdapter(this.todoAdapter);
        }
        return view;


    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecyclerListener) {
            mListener = (OnRecyclerListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

 */

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void insertToRecyclerView(@NonNull TodoRow todoRow) {

        this.todoList.add(0, todoRow);
        todoAdapter.notifyItemInserted(0);

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

        // startActivityForResult()の際に指定した識別コードとの比較
        if( requestCode == Constants.REQUEST_CODE ) {

            // 返却結果ステータスとの比較
            if (resultCode == Constants.RESULT_CODE) {

                // 返却されてきたintentから値を取り出す
                String str = data.getStringExtra(Constants.KEY_TITLE);

                TodoRow row = new TodoRow();
                row.setTitle(str);
                insertToRecyclerView(row);

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
