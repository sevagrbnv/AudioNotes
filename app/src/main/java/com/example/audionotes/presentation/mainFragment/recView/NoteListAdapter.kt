package com.example.audionotes.presentation.mainFragment.recView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.audionotes.R
import com.example.audionotes.databinding.ListItemBinding
import com.example.audionotes.domain.Note
import com.example.audionotes.utils.AudioPlayer
import com.example.audionotes.utils.getDuration

class NoteListAdapter : ListAdapter<Note, NoteViewHolder>(
    NoteDiffCallback()
) {
    var onAudioButtonClickListener: ((Note) -> Unit)? = null
    var onItemClickListener: ((Note) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item,
            parent,
            false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding

        when (binding) {
            is ListItemBinding -> {
                binding.titleMain.text = item.title
                binding.dateMain.text = item.date
                binding.timerMain.text = item.audioSource?.let { getDuration(it) }
                binding.audioButtomMain.setOnClickListener {
                    onAudioButtonClickListener?.invoke(item)
                }
                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }

}