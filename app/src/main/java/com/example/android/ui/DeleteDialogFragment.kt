package com.example.android.ui

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.R
import com.example.android.databinding.OptionDialogBinding
import com.example.android.viewmodel.NoteViewModel

class DeleteDialogFragment : DialogFragment(R.layout.option_dialog) {
    private val viewModel: NoteViewModel by lazy {
        ViewModelProvider(activity as MainActivity)[NoteViewModel::class.java]
    }
    private lateinit var binding: OptionDialogBinding
    private val noteId: Int by lazy {
        requireArguments().getInt(ID_KEY)
    }
    companion object {
        const val ID_KEY = "id"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true
        binding.deleteButton.setOnClickListener {
            viewModel.deleteNote(noteId)
            dialog?.dismiss()
            findNavController().navigate(R.id.actionGoToHomeFragment)
        }
        binding.discardButton.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OptionDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            val inset = InsetDrawable(
                ColorDrawable(Color.TRANSPARENT),
                16.toPx(), 16.toPx(), 16.toPx(), 16.toPx()
            )
            window?.apply {
                setBackgroundDrawable(inset)
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
    }
}

private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()