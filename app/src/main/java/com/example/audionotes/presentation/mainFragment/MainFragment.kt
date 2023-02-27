package com.example.audionotes.presentation.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.audionotes.R
import com.example.audionotes.databinding.FragmentMainBinding
import com.example.audionotes.presentation.mainFragment.recView.NoteListAdapter

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    //private lateinit var viewModel: MainViewModel
    private lateinit var noteListAdapter: NoteListAdapter

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

        //viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        //viewModel.todoList.observe(viewLifecycleOwner) {
        //    todoListAdapter.submitList(it)
        //    checkListForEmpty(binding.recView)
        //}
        setRecView(view)
//        binding.fab.setOnClickListener {
//            openSecondFragmentListener?.openSecondFragment(ADD_MODE, NEEDLES_KEY)
//        }
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_noteFragment)
        }
    }

    private fun setRecView(view: View) {
        noteListAdapter = NoteListAdapter()
        with(binding.recyclerView) {
            adapter = noteListAdapter
        }
        setCheckboxListener()
        setItemClickListener()
        setSwipeListener()
    }

    private fun setItemClickListener() {
//        noteListAdapter.onTodoItemClickListener = {
//            openSecondFragmentListener?.openSecondFragment(EDIT_MODE, it.id)
//        }
    }

    private fun setCheckboxListener() {
        noteListAdapter.onAudioButtonClickListener = {
            //viewModel.changeItemCompleteState(it)
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
                //viewModel.deleteTodoItem(item)
                //checkListForEmpty(rcViewTodoList)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    companion object {

    }
}