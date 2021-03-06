package com.example.todolist;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;




public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {


    private List<Note> notes = new ArrayList<>();
    private onItemClickListener itemListener, itemLongListener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);

    }


    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
        holder.imageViewIcon.setImageResource(currentNote.getIcon());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }




    // Holding single NoteItem
    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private ImageView imageViewIcon;
        public ImageView imageViewDoneIcon, imageViewDefaultIcon;


        // itemView is the view of the note item
        public NoteHolder(@NonNull final View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.text_view_title);
            textViewDescription = (TextView) itemView.findViewById(R.id.text_view_description);
            textViewPriority = (TextView) itemView.findViewById(R.id.text_view_priority);
            imageViewIcon = (ImageView) itemView.findViewById(R.id.icon_default_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (itemListener != null && position != RecyclerView.NO_POSITION) {
                        itemListener.onItemClick(notes.get(position));
                    }
                }
            });

            imageViewIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (itemListener != null && position != RecyclerView.NO_POSITION) {
                        itemListener.onDoneClick(position,notes.get(position));
                    }
                }
            });


        }
    }


    // the onClick note item
    public interface onItemClickListener {
        void onItemClick(Note note);
         void onDoneClick(int position,Note note);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.itemListener = listener;
    }


}
