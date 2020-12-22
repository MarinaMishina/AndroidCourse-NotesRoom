package com.sumin.notes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.locks.Lock;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase database;
    private static final String DB_NAME = "notes2.db";
    private static final Object LOCK = new Object();

    // Получение экземпляра базы данных. Работа с ОДНИМ экземпляром
    public static NotesDatabase getInstance(Context context) {
        // Блокировка потоков. В один момент с БД может работать только один поток
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, NotesDatabase.class, DB_NAME)
                        //.allowMainThreadQueries() //Разрешаем работу в главном потоке!!!! В РЕАЛЬНЫХ ПРИЛОЖЕНИЯХ ТАК ДЕЛАТЬ НЕЛЬЗЯ!!!!! Вся работа с БД должна проходить в потоке, отличном от главного
                        .build();
            }
        }
            return database;
    }

    public abstract NotesDao notesDao ();
}
