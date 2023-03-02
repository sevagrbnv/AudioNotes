package com.example.audionotes.presentation.mainFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        }
        Log.e("Sosiski", "2")
        setRecView()
        Log.e("Sosiski", "1")
        binding.fabAdd.setOnClickListener {
            val direction = MainFragmentDirections.actionMainFragmentToNoteFragment()
            findNavController().navigate(direction)
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
            val direction = MainFragmentDirections.actionMainFragmentToNoteFragment(it.id)
            findNavController().navigate(direction)
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
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

}