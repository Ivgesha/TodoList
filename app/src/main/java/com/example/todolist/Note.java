package com.example.todolist;


import android.widget.ImageView;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private String priority;

    private int icon;      // done or not.


    public Note(String title, String description, String priority, int icon) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.icon = icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public int getIcon() {
        return icon;
    }
}
