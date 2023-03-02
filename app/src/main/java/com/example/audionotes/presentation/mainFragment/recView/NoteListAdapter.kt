package com.example.audionotes.presentation.mainFragment.recView

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.audionotes.R
import com.example.audionotes.databinding.ListItemBinding
import com.example.audionotes.domain.Note
import com.example.audionotes.utils.FileContoller


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
        val fileContoller = FileContoller()

        when (binding) {
            is ListItemBinding -> {
                binding.titleMain.text = item.title
                binding.dateMain.text = item.date
                binding.timerMain.text = item.audioSource?.let { fileContoller.getDuration(it) }
                val buttonClick = AlphaAnimation(1f, 0.5f)
                binding.audioButtomMain.setOnClickListener {
                    it.startAnimation(buttonClick);
                    onAudioButtonClickListener?.invoke(item)
                }
                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }

}