package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.todolist.view.TodoList;

public class MainActivity extends AppCompatActivity {

    Button  ProceedButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ProceedButton = findViewById(R.id.ProceedButton);
    }

    public void ProceedClicked(View view){
        Intent intent = new Intent(this, TodoList.class);
        startActivity(intent);
    }
}
