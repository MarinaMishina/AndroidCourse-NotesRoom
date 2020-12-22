package com.sumin.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerDaysOfWeek;
    private RadioGroup radioGroupPriority;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerDaysOfWeek = findViewById(R.id.spinnerDaysOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);

    }

    public void onClickSaveNote(View view) {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        int dayOfWeek = spinnerDaysOfWeek.getSelectedItemPosition();
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());
        // Объект Note больше не нужен
        //Note note = new Note(title, description, dayOfWeek, priority);
        // Notes - приватная и AddNoteActivity ее не видит
        //MainActivity.notes.add(note);

        if (isFilled(title, description)) {
            Note note = new Note(title, description, dayOfWeek, priority);
            viewModel.insertNote(note);
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, R.string.warning_fill_fields, Toast.LENGTH_SHORT).show();
        }
    }
    // Проверка заполнения всех полей. Поля day_of_week и priority заполняются через чекбоксы и спиннер => имеют значения по умолчанию и не могут быть пустыми
    private boolean isFilled (String title, String description) {
        return !title.isEmpty() && !description.isEmpty();
    }
}
