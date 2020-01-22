package com.example.todolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.todolist.Note;
import com.example.todolist.NoteAdapter;
import com.example.todolist.R;
import com.example.todolist.viewModel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TodoList extends AppCompatActivity {
    Button ProceedButton;
FloatingActionButton floatingActionButton;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        floatingActionButton = findViewById(R.id.button_add_note);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // size dosent change
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        // we do it for not making many view models
        // and we pass "this" activity for it to destroy the view model of the current activity
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // update our recycler view
                adapter.setNotes(notes);

            }
        });

    }



// flaoting Button click
    public void floatingButtonClicked(View view){
        Intent intent = new Intent(this,AddNoteActivity.class);
        startActivity(intent);
    }

}
