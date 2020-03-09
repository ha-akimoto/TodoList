package com.example.todolist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.common.TodoComparator;
import com.example.todolist.common.TodoTextPaint;
import com.example.todolist.databinding.TodoRowBinding;
import com.example.todolist.listener.OnRecyclerListener;
import com.example.todolist.room.TodoEntity;
import com.example.todolist.viewmodel.TodoViewModel;

import java.util.Collections;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    //private List<TodoRow> todoList;
    private OnRecyclerListener listener;
    private TodoViewModel viewModel;

    public TodoAdapter(OnRecyclerListener listener, TodoViewModel viewModel) {
        this.listener = listener;
        this.viewModel = viewModel;
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

        final List<TodoEntity> entityList = this.viewModel.liveTodo.getValue();
        final TodoEntity entity = entityList.get(position);
        // カテゴリー名の表示

        String date = entity.endDate;
        if (entity.completeStatus) {
            if (position == 0 || (!entityList.get(position - 1).completeStatus)) {
                // 対象のタスクが完了済みで、一つ前のタスクが未完了の場合
                viewHolder.category.setVisibility(View.VISIBLE);
                viewHolder.category.setText("完了済み");
            } else {
                viewHolder.category.setVisibility(View.GONE);
            }
        } else if (position == 0 ||
                !date.equals(entityList.get(position - 1).endDate)) {
            // 対象のタスクの日付が、一つ前のタスクの日付と違う場合
            viewHolder.category.setVisibility(View.VISIBLE);
            viewHolder.category.setText(date);
        } else {
            viewHolder.category.setVisibility(View.GONE);
        }

        holder.getBinding().setViewModel(this.viewModel);
        holder.getBinding().setPosition(viewHolder.getAdapterPosition());
        //holder.getBinding().setTodoRow(entity);
        holder.getBinding().executePendingBindings();


        // 完了ステータスがTrueなら見た目を変更する
        if (entity.completeStatus) {
            TodoTextPaint.grayOut(holder.getTextView());

        } else {
            TodoTextPaint.restore(holder.getTextView());

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
        return viewModel.liveTodo.getValue().size();
    }


    /**
     * Todoタスクの削除
     *
     * @param position 削除するタスクのポジション
     */
    public void remove(int position) {

        // エンティティを作成して削除
        this.viewModel.delete(this.viewModel.liveTodo.getValue().get(position));
        // Todoリストから削除
        this.viewModel.liveTodo.getValue().remove(position);

        notifyDataSetChanged();
        //notifyItemRemoved(position);
    }

    /**
     * Todoタスクの追加
     *
     * @param entity 追加するタスク
     */
    public void insert(TodoEntity entity) {

        // DBに追加
        this.viewModel.insert(entity);

        // Todoリストに追加
        int id = this.viewModel.getMaxId();
        entity.id = id;
        List<TodoEntity> todoList = this.viewModel.liveTodo.getValue();
        todoList.add(entity);
        Collections.sort(todoList, new TodoComparator());
        this.viewModel.liveTodo.setValue(todoList);
        //int position = this.todoList.indexOf(entity);

        notifyDataSetChanged();

        //notifyItemInserted(position);
    }

    /**
     * Todoタスクの更新
     *
     * @param position 更新するタスクのポジション
     * @param entity   更新後のタスク
     */
    public void update(int position, TodoEntity entity) {

        // Todoリストの入れ替え
        List<TodoEntity> todoList = this.viewModel.liveTodo.getValue();
        todoList.remove(position);
        todoList.add(entity);

        // 並び替え
        //int from = position;
        Collections.sort(todoList, new TodoComparator());
        //int to = this.todoList.indexOf(entity);
        this.viewModel.liveTodo.setValue(todoList);

        // DB更新
        this.viewModel.update(entity);

        notifyDataSetChanged();
        //notifyItemMoved(from, to);
        //notifyItemChanged(to, entity);

    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private CheckBox checkBox;
        private TextView dateView;
        private ImageButton deleteButton;
        private TodoRowBinding binding;
        private TextView category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.deleteButton = itemView.findViewById(R.id.deleteButton);
            this.textView = itemView.findViewById(R.id.textView_title);
            this.dateView = itemView.findViewById(R.id.dateView);
            this.checkBox = itemView.findViewById(R.id.checkBox);
            this.binding = DataBindingUtil.bind(itemView);
            this.category = itemView.findViewById(R.id.category_text_view);


            // テキストボックスにリスナー設定
            this.textView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onRecyclerClicked(v
                            , viewModel.liveTodo.getValue().get(getAdapterPosition())
                            , getAdapterPosition());
                }
            });

            // チェックボックスにリスナー設定
            this.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    TodoEntity entity = viewModel.liveTodo.getValue().get(getAdapterPosition());

                    // todoListの完了ステータスとチェックボックスの状態が違う場合のみ処理を行う
                    if (isChecked != entity.completeStatus) {
                        // Todoリストの完了ステータスを設定する
                        viewModel.liveTodo.getValue().get(getAdapterPosition())
                                .completeStatus = isChecked;
                        // 完了ステータスがTrueの場合、テキストをグレーに変更して取消線を入れる
                        if (isChecked) {
                            TodoTextPaint.grayOut(textView);
                        } else {
                            // Falseの場合は戻す
                            TodoTextPaint.restore(textView);
                        }
                        // ローカルDBの更新
                        update(getAdapterPosition(), entity);

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

        public TodoRowBinding getBinding() {
            return binding;
        }
    }


}
