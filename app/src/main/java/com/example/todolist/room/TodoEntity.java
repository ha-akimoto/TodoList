package com.example.todolist.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo")
public class TodoEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "endDate")
    public String endDate;

    @ColumnInfo(name = "detail")
    public String detail;

    @ColumnInfo(name = "completeStatus")
    public boolean completeStatus;
}
