package com.example.audionotes.presentation.mainFragment

import android.content.Context
import android.os.Bundle
import android.provider.MediaStore.Audio
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.audionotes.R
import com.example.audionotes.databinding.FragmentMainBinding
import com.example.audionotes.presentation.mainFragment.recView.NoteListAdapter
import com.example.audionotes.utils.AudioPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var noteListAdapter: NoteListAdapter

    private lateinit var audioPlayer: AudioPlayer

    override fun onAttach(context: Context) {
        super.onAttach(context)
        audioPlayer = AudioPlayer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("Sosiski", "1")
        viewModel.noteList.observe(viewLifecycleOwner) {
            noteListAdapter.submitList(it)
            //checkListForEmpty()
        }
        Log.e("Sosiski", "2")
        setRecView()
        Log.e("Sosiski", "1")
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_noteFragment)
        }
    }

    private fun setRecView() {
        noteListAdapter = NoteListAdapter()
        with(binding.recyclerView) {
            adapter = noteListAdapter
        }
        setAudioButtonListener()
        setItemClickListener()
        setSwipeListener()
    }

    private fun setItemClickListener() {
        noteListAdapter.onItemClickListener = {
            Log.e("!!!", it.toString())
            //openSecondFragmentListener?.openSecondFragment(EDIT_MODE, it.id)
        }
    }

    private fun setAudioButtonListener() {
        noteListAdapter.onAudioButtonClickListener = { note ->
            if (audioPlayer.isPlaying() && !audioPlayer.isEnd()) {
                audioPlayer.stop()
            } else {
                note.audioSource?.let { it -> audioPlayer.start(it) }
                note.audioSource.toString()
            }
        }
    }

    private fun setSwipeListener() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = noteListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteTodoItem(item)
                //checkListForEmpty(rcViewTodoList)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    companion object {

    }
}