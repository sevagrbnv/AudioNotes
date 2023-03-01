package com.example.audionotes.presentation.mainFragment.recView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.audionotes.R
import com.example.audionotes.databinding.ListItemBinding
import com.example.audionotes.domain.Note
import com.example.audionotes.utils.AudioPlayer

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
            is ListItemBinding  -> {
                var isOn = false
                binding.titleMain.text = item.title
                binding.dateMain.text = item.date
                binding.timerMain.text = if (item.audioSource == null) "Empty" else "00:00"
                binding.audioButtomMain.setOnClickListener{
                    onAudioButtonClickListener?.invoke(item)
                }
                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }

}