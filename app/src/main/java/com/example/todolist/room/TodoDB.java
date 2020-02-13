package com.example.todolist.room;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {TodoEntity.class}, version = 2)
public abstract class TodoDB extends RoomDatabase {
    public abstract TodoDao todoDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE todo ;");
            database.execSQL("CREATE TABLE IF NOT EXISTS `todo` (`id` INTEGER " +
                    "PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `endDate` TEXT," +
                    " `detail` TEXT, `completeStatus` INTEGER NOT NULL)");
        }
    };

}
