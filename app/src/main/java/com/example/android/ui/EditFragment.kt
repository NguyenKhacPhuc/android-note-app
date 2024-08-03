package com.example.android.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.android.R
import com.example.android.databinding.EditFragmentBinding
import com.example.android.viewmodel.NoteViewModel
import com.example.domain.entity.NoteDomain
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EditFragment : BaseFragment<NoteViewModel, EditFragmentBinding>() {
    private val noteId: Int? by lazy {
        arguments?.getInt(ID_KEY)
    }
    private val noteTitle: String by lazy {
        arguments?.getString(TITLE_KEY) ?: ""
    }

    private val noteContent: String by lazy {
        arguments?.getString(CONTENT_KEY) ?: ""
    }

    private val noteImageLink: String by lazy {
        arguments?.getString(IMAGE_LINK_KEY) ?: ""
    }

    private val noteDateCreated: String by lazy {
        arguments?.getString(CREATED_DATE) ?: ""
    }

    private val hexColor: String by lazy {
        arguments?.getString(HEX_COLOR) ?: viewModel.getRandomHexColor()
    }

    companion object {
        const val ID_KEY = "id"
        const val TITLE_KEY = "title"
        const val CONTENT_KEY = "content"
        const val IMAGE_LINK_KEY = "imageLink"
        const val CREATED_DATE = "createdDate"
        const val HEX_COLOR = "hexColor"
    }

    override val modelClass: Class<NoteViewModel>
        get() = NoteViewModel::class.java
    override val isViewModelProvideByActivity: Boolean
        get() = true
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> EditFragmentBinding
        get() = EditFragmentBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        handleTitleUi()
        handleImageUi()
        handleContentUi()
        handleCreatedDateUi()
    }

    private fun setUpToolbar() {
        binding.toolbar.apply {
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.delete -> {
                        noteId?.let {
                            showDeleteDialog()
                        }
                        true
                    }

                    R.id.save -> {
                        showSavedDialog()
                        val noteDomain = NoteDomain(
                            title = binding.enterTitle.text.toString(),
                            content = binding.enterBody.text.toString(),
                            imageLink = noteImageLink,
                            createdDate = binding.dateCreated.text.toString(),
                            hexColor = hexColor
                        )

                        noteId?.let {
                            viewModel.updateNote(noteDomain.copy(
                                id = it
                            ))

                        } ?: run {
                            viewModel.insertNote(noteDomain)
                        }
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun showSavedDialog() {
        navController.navigateSafe(R.id.actionGoToSuccessDialogFragment)
    }

    private fun showDeleteDialog() {
        val bundle = bundleOf(DeleteDialogFragment.ID_KEY to noteId)
        navController.navigateSafe(R.id.actionGoToDeleteDialogFragment, bundle)
    }

    private fun handleTitleUi() {
        if (noteTitle.isNotEmpty()) {
            binding.enterTitle.setText(noteTitle)
        }
    }

    private fun handleImageUi() {
        if (noteImageLink.isNotEmpty()) {
            (activity as? MainActivity)?.loading()
            Glide
                .with(this)
                .load(noteImageLink)
                .override(1080, 500) // Set the desired banner dimensions
                .centerCrop()
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        (activity as? MainActivity)?.dismiss()
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(binding.imagePreview);
        }
    }

    private fun handleContentUi() {
        if (noteContent.isNotEmpty()) {
            binding.enterBody.setText(noteContent)
        }
    }

    private fun handleCreatedDateUi() {
        binding.dateCreated.text =
            noteDateCreated.ifEmpty { getCurrentFormattedDate() }
    }

    private fun getCurrentFormattedDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy")
        return currentDate.format(formatter)
    }
}