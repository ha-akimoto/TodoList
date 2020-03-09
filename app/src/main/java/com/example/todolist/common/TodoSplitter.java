package com.example.todolist.common;

import com.example.todolist.viewmodel.TodoRow;

import java.util.ArrayList;
import java.util.List;

public class TodoSplitter {

    public static List<List<TodoRow>> splitTodoList(final List<TodoRow> todoList) {
        List<TodoRow> targetList = new ArrayList<>();
        targetList.addAll(todoList);
        List<List<TodoRow>> resultList = new ArrayList<>();
        List<String> dateList = getDateList(todoList);

        // 日付リストごとにTodoRowのリストを作成する
        for (int dateIndex = 0; dateIndex < dateList.size(); dateIndex++) {
            String date = dateList.get(dateIndex);
            List<TodoRow> childTodoList = new ArrayList<>();

            // Todoリスト全部走査
            for (int tarIndex = 0; tarIndex < targetList.size(); tarIndex++) {
                String targetDate = targetList.get(tarIndex).getEndDate();

                // 日付リストの日付と一致するTodoRowを子リストに追加
                if (targetDate.equals(date)) {
                    childTodoList.add(targetList.get(tarIndex));
                    targetList.remove(tarIndex);
                }
            }
            // 作成した子リストをリザルトリストに追加
            resultList.add(childTodoList);
        }
        return resultList;
    }

    private static List<String> getDateList(final List<TodoRow> todoList) {
        List<String> dateList = new ArrayList<>();

        // Todoリストから日付リストを作成
        for (int i = 0; i < todoList.size(); i++) {
            String date = todoList.get(i).getEndDate();
            // 日付リストに対象の日付が含まれていなければ、追加する
            if (!dateList.contains(date)) {
                dateList.add(date);
            }
        }
        return dateList;
    }
}
