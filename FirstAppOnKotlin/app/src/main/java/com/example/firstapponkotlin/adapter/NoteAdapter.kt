package com.example.firstapponkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapponkotlin.databinding.ItemNoteBinding
import com.example.firstapponkotlin.model.Note
import com.example.firstapponkotlin.model.mapToColor

val DIFF_UTIL: DiffUtil.ItemCallback<Note> = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return true
    }
}

class NoteAdapter(val noteHandler: (Note) -> Unit) :
    ListAdapter<Note, NoteAdapter.NoteViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val noteBinding = ItemNoteBinding.inflate(layoutInflater, parent, false)
        return NoteViewHolder(noteBinding)

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(private val noteBinding: ItemNoteBinding) :
        RecyclerView.ViewHolder(noteBinding.root) {

        private lateinit var currentNote: Note

        private val noteClicked: View.OnClickListener = View.OnClickListener {
            noteHandler(currentNote)
        }
        fun bind(item: Note) {
            currentNote = item
            with(item) {
                noteBinding.title.text = title
                noteBinding.body.text = note
                noteBinding.root.setBackgroundColor(color.mapToColor(noteBinding.root.context))
                noteBinding.root.setOnClickListener(noteClicked)
            }
        }
    }
}