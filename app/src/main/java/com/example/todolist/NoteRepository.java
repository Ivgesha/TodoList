package com.example.todolist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;

    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase dataBase = NoteDatabase.getInstance(application);
        noteDao = dataBase.noteDao();
        allNotes = noteDao.getAllNotes();

    }

// we can't get to the DataBase direcly, so we will make AsyncTask for it.


    public void insert(Note note) {
        // instance of the InsertAsyncTask
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }


    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        // The noteDao which will work in the asyncTask
        // member variable ( only it can make operations on the dataBase )
        private NoteDao noteDao;

        // because we are in a static class, we can initialize the noteDao only with constructor.
        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            // we are inserting the only member we pass and it is the first one,
            // thats why we insert the notes[0]
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        // The noteDao which will work in the asyncTask
        // member variable ( only it can make operations on the dataBase )
        private NoteDao noteDao;

        // because we are in a static class, we can initialize the noteDao only with constructor.
        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            // we are inserting the only member we pass and it is the first one,
            // thats why we insert the notes[0]
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        // The noteDao which will work in the asyncTask
        // member variable ( only it can make operations on the dataBase )
        private NoteDao noteDao;

        // because we are in a static class, we can initialize the noteDao only with constructor.
        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            // we are inserting the only member we pass and it is the first one,
            // thats why we insert the notes[0]
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        public DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
