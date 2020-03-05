package com.example.todolist.common;

import com.example.todolist.room.TodoEntity;
import com.example.todolist.viewmodel.TodoRow;

import java.util.ArrayList;
import java.util.List;

public class TodoConverter {

    public static TodoEntity convertRowToEntity(final TodoRow row) {

        TodoEntity entity = new TodoEntity();
        entity.id = row.getId();
        entity.title = row.getTitle();
        entity.endDate = row.getEndDate();
        entity.detail = row.getDetail();
        entity.completeStatus = row.getCompleteStatus();

        return entity;
    }

    public static TodoRow convertEntityToRow(final TodoEntity entity) {
        TodoRow row = new TodoRow();
        row.setId(entity.id);
        row.setTitle(entity.title);
        row.setEndDate(entity.endDate);
        row.setDetail(entity.detail);
        row.setCompleteStatus(entity.completeStatus);

        return row;
    }

    public static List<TodoEntity> convertListRowToEntity(final List<TodoRow> rowList) {
        List<TodoEntity> entityList = new ArrayList<>();
        for (int i = 0; i < rowList.size(); i++) {
            entityList.add(convertRowToEntity(rowList.get(i)));
        }
        return entityList;
    }

    public static List<TodoRow> convertListEntityToRow(final List<TodoEntity> entityList) {
        List<TodoRow> rowList = new ArrayList<>();
        for (int i = 0; i < entityList.size(); i++) {
            rowList.add(convertEntityToRow(entityList.get(i)));
        }
        return rowList;

    }
}
