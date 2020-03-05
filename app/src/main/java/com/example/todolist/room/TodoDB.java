package com.example.todolist.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TodoEntity.class}, version = 2, exportSchema = false)
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

    private static volatile TodoDB INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TodoDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoDB.class, "todoDB")
                            //.allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
