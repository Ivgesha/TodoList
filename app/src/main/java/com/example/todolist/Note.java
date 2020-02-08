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

    private int priority;

    private int icon;      // done or not.

    private String phone;

    private String note;


    public Note(String title, String description, int priority, int icon, String phone) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.icon = icon;
        this.phone = phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNote(String note) {
        this.note = note;
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

    public int getPriority() {
        return priority;
    }

    public int getIcon() {
        return icon;
    }

    public String getPhone() {
        return phone;
    }

    public String getNote() {
        return note;
    }

    public void changeIcon(int iconResource){
        this.icon = iconResource;
    }

}
