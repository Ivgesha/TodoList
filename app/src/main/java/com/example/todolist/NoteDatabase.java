package com.example.todolist;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();


    // synchronized = only one Thread can get here.
    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
private NoteDao noteDao;

private PopulateDbAsyncTask(NoteDatabase db){
    noteDao = db.noteDao();
}

    @Override
    protected Void doInBackground(Void... voids) {
    noteDao.insert(new Note("title 1","Description 1","1",R.drawable.ic_default_icon));
        noteDao.insert(new Note("title 2","Description 2","2",R.drawable.ic_default_icon));
        noteDao.insert(new Note("title 3","Description 3","3",R.drawable.ic_default_icon));
        noteDao.insert(new Note("כותרת 4","תיאור 4","4",R.drawable.ic_default_icon));
        noteDao.insert(new Note("כותברת 5","תיאור 5","5",R.drawable.ic_default_icon));
        noteDao.insert(new Note("כותרת 6","תיאור 6","6",R.drawable.ic_default_icon));
        return null;
    }
}
}
