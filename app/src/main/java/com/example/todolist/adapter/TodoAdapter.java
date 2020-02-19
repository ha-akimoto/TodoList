package com.example.todolist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.common.Constants;
import com.example.todolist.common.TodoComparator;
import com.example.todolist.common.TodoTextPaint;
import com.example.todolist.listener.OnRecyclerListener;
import com.example.todolist.model.TodoRow;
import com.example.todolist.room.TodoDB;
import com.example.todolist.room.TodoEntity;
import com.example.todolist.room.TodoTableController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private ArrayList<TodoRow> todoList;
    private OnRecyclerListener listener;
    private TodoDB todoDB;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);

    public TodoAdapter(ArrayList<TodoRow> list, OnRecyclerListener listener, TodoDB todoDB) {
        this.todoList = list;
        Collections.sort(this.todoList, new TodoComparator());
        this.listener = listener;
        this.todoDB = todoDB;
    }


    /**
     * RecyclerView.Adapterで必須のメソッド
     * ViewHolderの作成を行う
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     */
    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 表示するレイアウトを設定
        return new TodoAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_row, parent, false));
    }

    /**
     * RecyclerView.Adapterで必須のメソッド
     * ViewHolderのバインドを行う
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.ViewHolder holder, int position) {
        final TodoAdapter.ViewHolder viewHolder = (TodoAdapter.ViewHolder) holder;

        final TodoRow todoRow = this.todoList.get(position);

        // テキストボックスのデータ表示
        holder.getTextView().setText(todoRow.getTitle());
        if (null != todoRow.getEndDate()) {
            holder.getDateView().setText(this.dateFormat.format(todoRow.getEndDate()));
        } else {
            holder.getDateView().setText("");
        }

        // 完了ステータスがTrueなら見た目を変更する
        if (todoRow.isCompleteStatus()) {
            TodoTextPaint.grayOut(holder.getTextView());
            holder.getCheckBox().setChecked(true);
        } else {
            TodoTextPaint.restore(holder.getTextView());
            holder.getCheckBox().setChecked(false);
        }
    }

    /**
     * RecyclerView.Adapterで必須のメソッド
     * 保持しているTodo Listのサイズを返却する
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return todoList.size();
    }


    /**
     * Todoタスクの削除
     *
     * @param position 削除するタスクのポジション
     */
    public void remove(int position) {
        // エンティティを作成して削除
        TodoRow row = this.todoList.get(position);
        TodoEntity entity = new TodoTableController().convertModelToEntity(row);
        this.todoDB.todoDao().delete(entity);
        // Todoリストから削除
        this.todoList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Todoタスクの追加
     *
     * @param row 追加するタスク
     */
    public void insert(TodoRow row) {
        // Todoリストに追加
        this.todoList.add(row);
        Collections.sort(this.todoList, new TodoComparator());
        int position = this.todoList.indexOf(row);
        // エンティティを作成
        TodoEntity entity = new TodoTableController().convertModelToEntity(row);
        // DB更新
        this.todoDB.todoDao().insertAll(entity);
        // 自動生成されたIDの取得
        this.todoList.get(position).setId(todoDB.todoDao().getMaxId());

        notifyItemInserted(position);
    }

    /**
     * Todoタスクの更新
     *
     * @param position 更新するタスクのポジション
     * @param row      更新後のタスク
     */
    public void update(int position, TodoRow row) {
        // Todoリストの入れ替え
        this.todoList.remove(position);
        this.todoList.add(position, row);
        // エンティティを作成
        TodoEntity entity = new TodoTableController().convertModelToEntity(row);
        // DB更新
        this.todoDB.todoDao().update(entity);

        // 移動
        int from = position;
        Collections.sort(this.todoList, new TodoComparator());
        int to = this.todoList.indexOf(row);
        notifyItemMoved(from, to);
        notifyItemChanged(to, row);

    }

    /**
     * TodoListの返却
     *
     * @return Todoリスト
     */
    public ArrayList<TodoRow> getTodoList() {
        return this.todoList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private CheckBox checkBox;
        private TextView dateView;
        private ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.textView_title);
            this.checkBox = itemView.findViewById(R.id.checkBox);
            this.dateView = itemView.findViewById(R.id.dateView);
            this.deleteButton = itemView.findViewById(R.id.deleteButton);

            // テキストボックスにリスナー設定
            this.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRecyclerClicked(v, todoList.get(getAdapterPosition()));

                }
            });

            // チェックボックスにリスナー設定
            this.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // todoListの完了ステータスとチェックボックスの状態が違う場合のみ処理を行う
                    if (isChecked != todoList.get(getAdapterPosition()).isCompleteStatus()) {
                        // Todoリストの完了ステータスを設定する
                        todoList.get(getAdapterPosition()).setCompleteStatus(isChecked);
                        // 完了ステータスがTrueの場合、テキストをグレーに変更して取消線を入れる
                        if (isChecked) {
                            TodoTextPaint.grayOut(textView);
                        } else {
                            // Falseの場合は戻す
                            TodoTextPaint.restore(textView);
                        }
                        // ローカルDBの更新
                        update(getAdapterPosition(), todoList.get(getAdapterPosition()));
                    }
                }
            });

            // 削除ボタンにリスナー設定
            getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(getAdapterPosition());
                }
            });
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
}
