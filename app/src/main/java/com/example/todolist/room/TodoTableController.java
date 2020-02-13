package com.example.todolist.room;

import com.example.todolist.common.Constants;
import com.example.todolist.model.TodoRow;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TodoTableController {

    private SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);

    public TodoEntity convertModelToEntity(TodoRow model) {

        TodoEntity entity = new TodoEntity();
        entity.id = model.getId();
        entity.title = model.getTitle();
        if (null != model.getEndDate()) {
            entity.endDate = this.dateFormat.format(model.getEndDate());
        }
        entity.detail = model.getDetail();
        entity.completeStatus = model.isCompleteStatus();

        return entity;
    }

    public TodoRow convertEntityToModel(TodoEntity entity) throws ParseException {
        TodoRow row = new TodoRow();
        row.setId(entity.id);
        row.setTitle(entity.title);
        if (null != entity.endDate && !entity.endDate.isEmpty()) {
            row.setEndDate(this.dateFormat.parse(entity.endDate));
        }
        row.setDetail(entity.detail);
        row.setCompleteStatus(entity.completeStatus);

        return row;
    }
}
