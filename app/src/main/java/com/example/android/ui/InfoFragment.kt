package com.example.android.ui

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.android.R

class InfoFragment : DialogFragment(R.layout.information_fragment) {

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
