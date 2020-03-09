package com.example.todolist.common;

import com.example.todolist.room.TodoEntity;

import java.util.Comparator;

public class TodoComparator implements Comparator<TodoEntity> {

    @Override
    public int compare(TodoEntity o1, TodoEntity o2) {
        // 第一条件：完了ステータスFalseが上、Trueが下
        if (o1.completeStatus && !o2.completeStatus) {
            return 1;
        } else if (!o1.completeStatus && o2.completeStatus) {
            return -1;
        } else {
            // 第二条件：期限日の昇順（期限日が入力されてない要素は下へ）
            if (null == o1.endDate) {
                return 1;
            } else if (null == o2.endDate) {
                return -1;
            } else {
                return o1.endDate.compareTo(o2.endDate);
            }
        }
    }
}
