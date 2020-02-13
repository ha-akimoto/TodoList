package com.example.todolist.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM todo")
    List<TodoEntity> getAll();

    @Query("SELECT id FROM todo ORDER BY id DESC LIMIT 1")
    int getMaxId();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TodoEntity... todo);

    @Delete
    void delete(TodoEntity... todo);

    @Update
    void update(TodoEntity todo);
}
