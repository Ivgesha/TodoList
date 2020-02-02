package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todolist.view.TodoList;

public class MainActivity extends AppCompatActivity {

public static final String EXTRA_NAME = "com.example.todolist.EXTRA_NAME";

    Button  ProceedButton;
    EditText editTextEnterName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ProceedButton = findViewById(R.id.ProceedButton);
        editTextEnterName = findViewById(R.id.edit_text_enter_name);
    }

    public void ProceedClicked(View view){
        Intent intent = new Intent(this, TodoList.class);
        String name = editTextEnterName.getText().toString();
        if (!name.equals(null)){
            intent.putExtra(EXTRA_NAME,name);
        }
        startActivity(intent);
    }
}
