package com.example.todolist.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.R;

// episod 7 13:28
public class AddEditNoteActivity extends AppCompatActivity {

    public static final int REQUEST_CALL = 1;

    public static final String EXTRA_ID = "com.example.todolist.view.EXTRA_ID";

    public static final String EXTRA_TITLE = "com.example.todolist.view.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION = "com.example.todolist.view.EXTRA_DESCRIPTION";

    public static final String EXTRA_PRIORITY = "com.example.todolist.view.EXTRA_PRIORITY";

    public static final String EXTRA_PHONE = "com.example.todolist.view.EXTRA_PHONE";

    public static final String EXTRA_NOTE = "com.example.todolist.view.EXTRA_NOTE";

    //public static final String EXTRA_BUNDLE = "com.example.todolist.view.EXTRA_BUNDLE";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPriority;
    private EditText editTextPhone;
    private EditText editTextNote;
    private Button btnSave, btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextPriority = findViewById(R.id.edit_text_priority);
        editTextPhone = findViewById(R.id.edit_text_phone);
        editTextNote = findViewById(R.id.edit_text_note);
        btnSave = findViewById(R.id.button_save);
        btnCancel = findViewById(R.id.button_cancel);

        // the intent that starts the activity
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            Log.d("EXTRA_ID_TEST", "entered extra id ");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            //Log.d("EXTRA_PRIORIT","test 1 "+ intent.getStringExtra(EXTRA_PRIORITY) );
            //Log.d("EXTRA_PRIORIT","test 2 "+ intent.getIntExtra(EXTRA_PRIORITY,0) );
            int priorityInt = intent.getIntExtra(EXTRA_PRIORITY, 0);
            String priorityString = String.valueOf(priorityInt);
            editTextPriority.setText(priorityString);
            editTextPhone.setText(intent.getStringExtra(EXTRA_PHONE));
            editTextNote.setText(intent.getStringExtra(EXTRA_NOTE));
        }


    }


    public void saveNote(View view) {

        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = Integer.parseInt(editTextPriority.getText().toString());
        Log.d("priority ", "" + priority);
        String phone = editTextPhone.getText().toString();
        String note = editTextNote.getText().toString();


        if (title.trim().isEmpty()) {
            Toast.makeText(this, "The title is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent returnedIntent = new Intent();
        returnedIntent.putExtra(EXTRA_TITLE, title);
        returnedIntent.putExtra(EXTRA_DESCRIPTION, description);
        returnedIntent.putExtra(EXTRA_PRIORITY, priority);            // passing int
        returnedIntent.putExtra(EXTRA_PHONE, phone);
        returnedIntent.putExtra(EXTRA_NOTE, note);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            returnedIntent.putExtra(EXTRA_ID, id);
        }

        //returnedIntent.putExtra(EXTRA_TITLE,title);
        //returnedIntent.putExtra(EXTRA_DESCRIPTION,description);
        //returnedIntent.putExtra(EXTRA_PRIORITY,priority);

        Log.d("titleTest", "put extra in addNoteActivity " + title);
        setResult(Activity.RESULT_OK, returnedIntent);
        finish();
    }

    public void noteCanceled(View view) {
        setResult(Activity.RESULT_CANCELED, null);
        finish();
    }

    public void onClickCallBtn(View view) {

        String number = editTextPhone.getText().toString().trim();
        if (number.length() > 0) {
            if (ContextCompat.checkSelfPermission(AddEditNoteActivity.this, Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddEditNoteActivity.this,new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
            } else {// if permission granted
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(AddEditNoteActivity.this, "Enter Phone Number", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                onClickCallBtn(null);
            }else
                Toast.makeText(AddEditNoteActivity.this, "Permission DENIED", Toast.LENGTH_LONG).show();
        }
    }
}
