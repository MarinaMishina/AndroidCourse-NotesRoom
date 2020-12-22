package com.sumin.notes;

import android.app.Application;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private NotesDatabase database;
    LiveData<List<Note>> notes;

    //Фоновый поток для вставки
    private HandlerThread insertTask;
    //Фоновый поток для удаления
    private HandlerThread deleteTask;
    //Фоновый поток для удаления всех заметок
    private HandlerThread deleteAllTask;


    public MainViewModel(@NonNull Application application) {
        super(application);
        database = NotesDatabase.getInstance(getApplication());
        notes = database.notesDao().getAllNotes();
    }


    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public HandlerThread getInsertTask() {
        return insertTask;
    }

    public HandlerThread getDeleteTask() {
        return deleteTask;
    }

    public HandlerThread getDeleteAllTask() {
        return deleteAllTask;
    }

    public void insertNote(Note note) {
        createThreadForInsert(note);
    }

    public void deleteNote(Note note) {
        createThreadForDelete(note);
    }

    public void deleteAllNotes() {
        createThreadForDeleteAll();
    }

    // AsyncTask устарел

    //Создаем фоновый поток для вставки записи в БД
    public void createThreadForInsert(Note note) {
        insertTask = new HandlerThread("InsertTask");
        insertTask.start();
        final Handler insertTaskHandler = new Handler(insertTask.getLooper());
        insertTaskHandler.post(new Runnable() {
            @Override
            public void run() {
                if (notes != null) {
                    database.notesDao().insertNote(note);
                    //Toast.makeText(getApplication().getApplicationContext(), "поток создан", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Создаем фоновый поток для удаления записи из БД
    public void createThreadForDelete(Note note) {
        deleteTask = new HandlerThread("DeleteTask");
        deleteTask.start();
        final Handler deleteTaskHandler = new Handler(deleteTask.getLooper());
        deleteTaskHandler.post(new Runnable() {
            @Override
            public void run() {
                if (notes != null) {
                    database.notesDao().deleteNote(note);
                    //Toast.makeText(getApplication().getApplicationContext(), "поток создан", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Создаем фоновый поток для удаления всех записей из БД
    public void createThreadForDeleteAll() {
        deleteAllTask = new HandlerThread("DeleteAllTask");
        deleteAllTask.start();
        final Handler deleteAllTaskHandler = new Handler(deleteAllTask.getLooper());
        deleteAllTaskHandler.post(new Runnable() {
            @Override
            public void run() {
                    database.notesDao().deleteAllNotes();
                    //Toast.makeText(getApplication().getApplicationContext(), "поток создан", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
