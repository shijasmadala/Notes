package com.products.testapp.add_note.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.products.testapp.R
import com.products.testapp.add_note.domain.model.NotesModel
import com.products.testapp.databinding.FragmentAddNewNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddNewNoteFragment : Fragment(R.layout.fragment_add_new_note) {
    private lateinit var binding: FragmentAddNewNoteBinding
    private val viewModel by viewModels<AddNoteViewModel>()
    private val args: AddNewNoteFragmentArgs by navArgs()
    var notesModel : NotesModel? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddNewNoteBinding.bind(view)
        observeAddNote()
        setListener()
    }

    private fun observeAddNote() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noteState.collect() {
                    when (it) {
                        is AddNoteState.AddNoteSuccess -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }

                        is AddNoteState.GetNoteById -> {
                            binding.apply {
                                title.setText( it.notesModel.title)
                                noteEd.setText(it.notesModel.contentNotes)
                                notesModel = it.notesModel
                            }
                        }

                        is AddNoteState.UpdateNote -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }

                        is AddNoteState.DeleteNote -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }

                        is AddNoteState.Loading -> {}

                        is AddNoteState.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setListener() {
        binding.apply {

            delete.isVisible = args.noteId != 0

            binding.saveNote.setOnClickListener {
                val title = title.text.toString()
                val content = noteEd.text.toString()
                if (args.noteId == 0) {
                    val addNote = NotesModel(id = 0, title = title, contentNotes = content)
                    viewModel.addNewNote(addNote)
                } else {
                    val updateNote = NotesModel(id = args.noteId, title = title, contentNotes = content)
                    viewModel.updateUser(updateNote)
                }
            }

            delete.setOnClickListener {
                notesModel?.let { it1 -> viewModel.deleteNote(it1) }
            }

            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

}