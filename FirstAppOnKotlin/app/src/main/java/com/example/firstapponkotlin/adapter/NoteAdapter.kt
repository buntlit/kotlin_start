package com.example.firstapponkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapponkotlin.data.Note
import com.example.firstapponkotlin.databinding.ItemNoteBinding

val DIFF_UTIL: DiffUtil.ItemCallback<Note> = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return true
    }
}

class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val noteBinding = ItemNoteBinding.inflate(layoutInflater, parent, false)
        return NoteViewHolder(noteBinding)

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NoteViewHolder(private val noteBinding: ItemNoteBinding) :
        RecyclerView.ViewHolder(noteBinding.root) {

        fun bind(notes: Note) {
            with(notes) {
                noteBinding.title.text = title
                noteBinding.body.text = note
                noteBinding.root.setBackgroundColor(color)
            }
        }
    }
}