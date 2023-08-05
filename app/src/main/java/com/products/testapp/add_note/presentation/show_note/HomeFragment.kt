package com.products.testapp.add_note.presentation.show_note

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.products.testapp.R
import com.products.testapp.add_note.domain.model.NotesModel
import com.products.testapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) , NoteAdapter.OnClick {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeFragmentViewModel>()
    private val noteAdapter by lazy { NoteAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        binding.recyclerView.adapter = noteAdapter


        observeShowNotes()
        binding.addNewNoteImg.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddNewNoteFragment(noteId = 0))
        }
    }

    private fun observeShowNotes(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.notes.collectLatest {
                    noteAdapter.submitList(it)
                }
            }
        }
    }

    override fun updateUser(notesModel: NotesModel) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddNewNoteFragment(noteId = notesModel.id))
    }

}