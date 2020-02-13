package com.example.todolist.common;

import com.example.todolist.model.TodoRow;

import java.util.Comparator;

public class TodoComparator implements Comparator<TodoRow> {

    @Override
    public int compare(TodoRow o1, TodoRow o2) {
        // 第一条件：完了ステータスFalseが上、Trueが下
        if (o1.isCompleteStatus() && !o2.isCompleteStatus()) {
            return 1;
        } else if (!o1.isCompleteStatus() && o2.isCompleteStatus()) {
            return -1;
        } else {
            // 第二条件：期限日の昇順（期限日が入力されてない要素は下へ）
            if (null == o1.getEndDate()) {
                return 1;
            } else if (null == o2.getEndDate()) {
                return -1;
            } else {
                return o1.getEndDate().compareTo(o2.getEndDate());
            }
        }
    }
}
