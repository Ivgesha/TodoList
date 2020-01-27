package com.example.todolist.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.todolist.Note;
import com.example.todolist.NoteAdapter;
import com.example.todolist.R;
import com.example.todolist.viewModel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.todolist.view.AddNoteActivity.EXTRA_DESCRIPTION;
import static com.example.todolist.view.AddNoteActivity.EXTRA_PRIORITY;
import static com.example.todolist.view.AddNoteActivity.EXTRA_TITLE;

public class TodoList extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;

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


    // floatingButton click
    public void floatingButtonClicked(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE_REQUEST);
    }


    // waiting for answer from the addNoteActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String title;
        String description;
        int priority;



        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            Log.d("titleTest", "entered to onActivityResult");
            Bundle extras = data.getExtras();
            // checking if we get data from addNoteActivity
            if (extras == null) {
                Log.d("titleTest", "didnt get the extras");
            } else {
                Log.d("titleTest", "got the extras");
                title = extras.getString(EXTRA_TITLE);
                description = extras.getString(EXTRA_DESCRIPTION);
                priority = extras.getInt(EXTRA_PRIORITY, 1);

                //          icon test               //
                int icon = R.drawable.ic_default_icon;
                //          icon test               //

                Note note = new Note(title, description, priority, icon);
                noteViewModel.insert(note);
                Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
            }


        } else if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Note canceled", Toast.LENGTH_SHORT).show();
        }
    }
}
