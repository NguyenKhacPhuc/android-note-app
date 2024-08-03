package com.example.android.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.databinding.NoteItemBinding
import com.example.domain.entity.NoteDomain

class NotesAdapter : ListAdapter<NoteDomain, NotesAdapter.NoteHolder>(NoteDiffCallback()) {
    var onNoteClick: (NoteDomain) -> Unit = {}
    inner class NoteHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteDomain) {
            binding.root.setOnClickListener {
                onNoteClick(note)
            }
            binding.title.text = note.title
            binding.root.setCardBackgroundColor(Color.parseColor(note.hexColor))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding =
            NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NoteDiffCallback : DiffUtil.ItemCallback<NoteDomain>() {
        override fun areItemsTheSame(oldItem: NoteDomain, newItem: NoteDomain): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: NoteDomain, newItem: NoteDomain): Boolean {
            return oldItem == newItem
        }
    }

}