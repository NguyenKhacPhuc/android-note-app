package com.example.android.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.core.os.trace
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.R
import com.example.android.adapter.NotesAdapter
import com.example.android.databinding.HomeFragmentBinding
import com.example.android.viewmodel.NoteViewModel

class HomeFragment : BaseFragment<NoteViewModel, HomeFragmentBinding>() {

    private val notesAdapter = NotesAdapter()
    override val isViewModelProvideByActivity: Boolean = true
    override val modelClass: Class<NoteViewModel>
        get() = NoteViewModel::class.java

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> HomeFragmentBinding
        get() = HomeFragmentBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun processLogicAfterViewCreated() {
        binding.listNotes.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            binding.listNotes.adapter = notesAdapter
        }
        viewModel.getAllNote()
        setUpListener()
        setUpLiveData()
    }

    private fun setUpLiveData() {
        viewModel.listNote.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.noNotesLayout.visibility = android.view.View.VISIBLE
            } else {
                binding.noNotesLayout.visibility = android.view.View.GONE
            }
            notesAdapter.submitList(it)
        }
    }

    private fun setUpListener() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.info -> {
                    // Handle info icon press
                    navController.navigateSafe(R.id.actionGoToInfoFragment)
                    true
                }
                else -> false
            }
        }
        binding.floatingActionButton.setOnClickListener {
            navController.navigateSafe(R.id.actionGoToAddNoteFragment)
        }
        notesAdapter.onNoteClick = { note ->
            val bundle = bundleOf(EditFragment.ID_KEY to note.id,
                EditFragment.CONTENT_KEY to note.content,
                EditFragment.IMAGE_LINK_KEY to note.imageLink,
                EditFragment.TITLE_KEY to note.title,
                EditFragment.CREATED_DATE to note.createdDate,
                EditFragment.HEX_COLOR to note.hexColor
            )

            navController.navigateSafe(R.id.actionGoToAddNoteFragment, bundle)
        }
    }
}