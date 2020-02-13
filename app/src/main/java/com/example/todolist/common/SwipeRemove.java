package com.example.todolist.common;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.adapter.TodoAdapter;

public class SwipeRemove extends ItemTouchHelper.SimpleCallback {

    TodoAdapter adapter;

    /**
     * コンストラクター
     */
    public SwipeRemove(int dragDirs, int swipeDirs, TodoAdapter adapter) {
        super(dragDirs, swipeDirs);
        this.adapter = adapter;
    }

    /**
     * ItemTouchHelper.SimpleCallbackで必須のメソッド
     * ドラッグアンドドロップでデータの入れ替えを行うときに使うが
     * 今回は使わないのでFalseを返す
     *
     * @return 入れ替え完了のステータス
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * ItemTouchHelper.SimpleCallbackで必須のメソッド
     * スワイプされた際の処理
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // 横にスワイプされたら要素を消す
        int swipedPosition = viewHolder.getAdapterPosition();
        this.adapter.remove(swipedPosition);
    }
}
