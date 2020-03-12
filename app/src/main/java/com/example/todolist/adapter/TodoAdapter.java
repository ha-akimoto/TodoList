package com.example.todolist.adapter;

import android.os.Handler;
import android.text.format.DateFormat;
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
import com.example.todolist.common.Constants;
import com.example.todolist.common.TodoComparator;
import com.example.todolist.common.TodoTextPaint;
import com.example.todolist.databinding.TodoRowBinding;
import com.example.todolist.listener.OnRecyclerListener;
import com.example.todolist.room.TodoEntity;
import com.example.todolist.viewmodel.TodoViewModel;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

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

        int adapterPosition = viewHolder.getAdapterPosition();
        final List<TodoEntity> entityList = this.viewModel.liveTodo.getValue();
        final TodoEntity entity = entityList.get(adapterPosition);

        // バインディング
        holder.getBinding().setViewModel(this.viewModel);
        holder.getBinding().setPosition(viewHolder.getAdapterPosition());
        holder.getBinding().executePendingBindings();

        viewHolder.category.setVisibility(View.GONE);
        viewHolder.categoryDate.setVisibility(View.GONE);

        String today = DateFormat.format(Constants.DATE_FORMAT, Calendar.getInstance()).toString();
        String date = entity.endDate;

        // カテゴリー名表示ポジションの取得
        int topCompletedIndex = -1;
        int topTodayIndex = -1;
        int topAfterTomorrowIndex = -1;
        int topExpiredIndex = -1;
        for (int i = 0; i < entityList.size(); i++) {
            if (entityList.get(i).completeStatus) {
                // 対象のタスクが完了済みの場合
                if (topCompletedIndex == -1) {
                    topCompletedIndex = i;
                }
            } else {
                // 対象のタスクが未完了の場合
                if (0 < today.compareTo(entityList.get(i).endDate)) {
                    // 対象のタスクの日付が本日以前の場合
                    if (topExpiredIndex == -1) {
                        topExpiredIndex = i;
                    }
                } else if (today.equals(entityList.get(i).endDate)) {
                    // 対象のタスクが本日の場合
                    if (topTodayIndex == -1) {
                        topTodayIndex = i;
                    }
                } else {
                    // 対象のタスクが明日以降の場合
                    if (topAfterTomorrowIndex == -1) {
                        topAfterTomorrowIndex = i;
                    }
                }
            }
        }

        // カテゴリー名の設定
        if (entity.completeStatus) {
            // 対象のタスクが完了済みの場合
            viewHolder.category.setText(R.string.category_completed);
        } else {
            // 対象のタスクが未完了の場合
            if (0 < today.compareTo(entity.endDate)) {
                // 対象のタスクの日付が本日以前の場合
                viewHolder.category.setText(R.string.category_expired);
            } else if (today.equals(entity.endDate)) {
                // 対象のタスクが本日の場合
                viewHolder.category.setText(R.string.category_today);
            } else {
                // 対象のタスクが明日以降の場合
                viewHolder.category.setText(R.string.category_after_tomorrow);
            }
        }

        // カテゴリー名の表示
        if (topCompletedIndex == viewHolder.getAdapterPosition()
                || topTodayIndex == viewHolder.getAdapterPosition()
                || topAfterTomorrowIndex == viewHolder.getAdapterPosition()
                || topExpiredIndex == viewHolder.getAdapterPosition()) {
            viewHolder.category.setVisibility(View.VISIBLE);
        }

        // カテゴリー下線の表示
        if (viewHolder.category.getVisibility() == View.VISIBLE) {
            viewHolder.categoryLine.setVisibility(View.VISIBLE);
        } else {
            viewHolder.categoryLine.setVisibility(View.GONE);
        }

        // カテゴリー日付表示非表示
        if (!entity.completeStatus
                && today.equals(entity.endDate)
                && viewHolder.category.getVisibility() == View.VISIBLE) {
            // 対象のタスクが未完了、日付が本日、カテゴリーが表示されている場合
            viewHolder.categoryDate.setVisibility(View.VISIBLE);
        } else {
            // それ以外の場合
            viewHolder.categoryDate.setVisibility(View.GONE);
        }

        // 日付表示非表示
        if (entity.completeStatus) {
            // 完了済みの場合
            viewHolder.dateView.setVisibility(View.VISIBLE);
        } else if (today.equals(date)) {
            // 日付が本日の場合
            viewHolder.dateView.setVisibility(View.GONE);
        } else {
            // それ以外の場合
            viewHolder.dateView.setVisibility(View.VISIBLE);
        }

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

        notifyDataSetChangedDelayed();
        notifyItemRemoved(position);
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
        int position = this.viewModel.liveTodo.getValue().indexOf(entity);

        notifyDataSetChangedDelayed();

        notifyItemInserted(position);
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
        int from = position;
        Collections.sort(todoList, new TodoComparator());
        int to = this.viewModel.liveTodo.getValue().indexOf(entity);
        this.viewModel.liveTodo.setValue(todoList);

        // DB更新
        this.viewModel.update(entity);

        notifyDataSetChangedDelayed();

        notifyItemMoved(from, to);
        notifyItemChanged(to, entity);

    }

    private void notifyDataSetChangedDelayed() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        };
        handler.postDelayed(runnable, 500);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private CheckBox checkBox;
        private TextView dateView;
        private ImageButton deleteButton;
        private TodoRowBinding binding;
        private TextView category;
        private TextView categoryDate;
        private View categoryLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.deleteButton = itemView.findViewById(R.id.deleteButton);
            this.textView = itemView.findViewById(R.id.textView_title);
            this.dateView = itemView.findViewById(R.id.dateView);
            this.checkBox = itemView.findViewById(R.id.checkBox);
            this.binding = DataBindingUtil.bind(itemView);
            this.category = itemView.findViewById(R.id.category_text_view);
            this.categoryDate = itemView.findViewById(R.id.category_date_text_view);
            this.categoryLine = itemView.findViewById(R.id.category_line);

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
