package com.example.todolist.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.Note;
import com.example.todolist.NoteAdapter;
import com.example.todolist.R;
import com.example.todolist.viewModel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.todolist.MainActivity.EXTRA_NAME;
import static com.example.todolist.view.AddEditNoteActivity.EXTRA_DESCRIPTION;
import static com.example.todolist.view.AddEditNoteActivity.EXTRA_ICON;
import static com.example.todolist.view.AddEditNoteActivity.EXTRA_ID;
import static com.example.todolist.view.AddEditNoteActivity.EXTRA_NOTE;
import static com.example.todolist.view.AddEditNoteActivity.EXTRA_PHONE;
import static com.example.todolist.view.AddEditNoteActivity.EXTRA_PRIORITY;
import static com.example.todolist.view.AddEditNoteActivity.EXTRA_TITLE;
import static com.example.todolist.view.AddEditNoteActivity.EXTRA_URL;

public class TodoList extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;


    Button ProceedButton;
    private FloatingActionButton floatingActionButton;
    private ImageView defaultIconButton, doneIconButton;
    private NoteViewModel noteViewModel;
    private Bundle bundle;
    private TextView textViewHelloTitle;
    private ImageButton imageViewMainMenu;
    private Toolbar toolbar;
    private LinearLayout todoLinearLayout;
    private Switch switchToggleColor;

    private boolean mBold = false;
    private boolean mItalic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        switchToggleColor = findViewById(R.id.switch_toggle_color);
        switchToggleColor.setChecked(false);
        switchToggleColor.setTextOn("On");
        switchToggleColor.setTextOff("Off");

        todoLinearLayout = findViewById(R.id.todo_linearlayout);

        //imageViewMainMenu = findViewById(R.id.image_button_main_menu);

        textViewHelloTitle = findViewById(R.id.hello_title);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.button_add_note);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // size dosent change
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);
        //recyclerView.setBackgroundColor(Color.BLACK);


        bundle = getIntent().getExtras();
        String passedName = bundle.getString(EXTRA_NAME);
        if (!passedName.equals("")) {
            textViewHelloTitle.setText(getString(R.string.hello) + " " + passedName);
        } else {
            textViewHelloTitle.setText(getString(R.string.hello_customer));
        }


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


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Log.d("onMove", "onMoveActivated");
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(TodoList.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NoteAdapter.onItemClickListener() {


            // clicked on the note item
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(TodoList.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
                intent.putExtra(AddEditNoteActivity.EXTRA_PHONE, note.getPhone());
                intent.putExtra(AddEditNoteActivity.EXTRA_URL,note.getUrl());
                intent.putExtra(AddEditNoteActivity.EXTRA_NOTE,note.getNote());
                intent.putExtra(AddEditNoteActivity.EXTRA_ICON, note.getIcon());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }

            @Override
            public void onDoneClick(int position, Note note) {
                updateIcon(note);
                noteViewModel.update(note);
            }
        });

    }


    // floatingButton click
    public void floatingButtonClicked(View view) {
        Intent intent = new Intent(this, AddEditNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE_REQUEST);
    }


    // waiting for answer from the addNoteActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String title, description, phone, url,noteEdit;
        int priority;

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            // checking if we get data from addNoteActivity
            if (extras == null) {
                Log.d("titleTest", "didnt get the extras");
            } else {
                title = extras.getString(EXTRA_TITLE);
                description = extras.getString(EXTRA_DESCRIPTION);
                priority = extras.getInt(EXTRA_PRIORITY, 1);
                phone = extras.getString(EXTRA_PHONE);
                url = extras.getString(EXTRA_URL);
                noteEdit = extras.getString(EXTRA_NOTE);

                //          icon test               //
                int icon = R.drawable.ic_default_icon;
                //          icon test               //

                Note note = new Note(title, description, priority, icon, phone,url,noteEdit);
                noteViewModel.insert(note);
                Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Note cant be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            Bundle extras = data.getExtras();
            title = extras.getString(EXTRA_TITLE);
            description = extras.getString(EXTRA_DESCRIPTION);
            //priority = extras.getInt(EXTRA_PRIORITY, 1);
            priority = extras.getInt(EXTRA_PRIORITY, 0);
            phone = extras.getString(EXTRA_PHONE);
            url = extras.getString(EXTRA_URL);
            noteEdit = extras.getString(EXTRA_NOTE);
            int icon = extras.getInt(EXTRA_ICON);


            Note note = new Note(title, description, priority, icon, phone,url,noteEdit);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Note canceled", Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    // change the text to italic and bold with check boxs
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backgroundChange_0:
                // change background
                todoLinearLayout.setBackgroundResource(R.drawable.background_1);
                break;
            case R.id.backgroundChange_1:
                todoLinearLayout.setBackgroundResource(R.drawable.background_0);
                break;
            case R.id.font_change:
                updateTitle(item);
            default:
                break;
            //return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);


    }


    public void updateTitle(@NonNull MenuItem item) {
        if (item.isChecked()) {
            item.setChecked(false);

            if (mBold && mItalic) {
                textViewHelloTitle.setTypeface(null, Typeface.BOLD_ITALIC);
            }
            mBold = false;
            mItalic = false;
        } else {
            item.setChecked(true);
            textViewHelloTitle.setTypeface(null, Typeface.NORMAL);
        }
        mBold = true;
        mItalic = true;
    }


    public void switchColorToggleOnClick(View view) {

        boolean switchStatus = switchToggleColor.isChecked();
        if (switchStatus == true) {
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBackground_0)));
        } else {
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBackground_1)));

        }
    }


    public void updateIcon(Note note) {
        if (note.getIcon() == R.drawable.ic_default_icon) {
            note.changeIcon(R.drawable.ic_done_icon);
        } else
            note.changeIcon(R.drawable.ic_default_icon);
    }
}
