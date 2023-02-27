package com.example.audionotes.presentation.mainFragment.recView

import androidx.recyclerview.widget.DiffUtil
import com.example.audionotes.domain.Note

class NoteDiffCallback: DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}