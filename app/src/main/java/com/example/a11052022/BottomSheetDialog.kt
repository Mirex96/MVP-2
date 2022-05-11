package com.example.a11052022

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.example.a11052022.BottomSheetDialogAction.*
import com.example.a11052022.R.id.onDeleteBs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

const val KEY_REQUEST_BS = "KEY_REQUEST_BS"
const val KEY_BUNDLE_TYPE = "BottomSheetDialogAction"
const val BUNDLE_KEY_PERSON = "BUNDLE_KEY_PERSON"

enum class BottomSheetDialogAction {
    EDIT,
    DELETE,
    CLONE,

}


class BottomSheetDialog : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_dialog, container, false)
    }


    private fun onClickResult(action: BottomSheetDialogAction) {
        setFragmentResult(
            KEY_REQUEST_BS, bundleOf(
                KEY_BUNDLE_TYPE to action,
                BUNDLE_KEY_PERSON to arguments?.getParcelable<Person>(PERSON)
            )
        )
        dismiss()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(onDeleteBs).setOnClickListener {
            onClickResult(DELETE)
            dismiss()
        }
        view.findViewById<Button>(R.id.onCloneBs).setOnClickListener {
            onClickResult(CLONE)
            dismiss()
        }
        view.findViewById<Button>(R.id.onEditBs).setOnClickListener {
            onClickResult(EDIT)
            dismiss()
        }
        view.findViewById<Button>(R.id.exitButtonBs).setOnClickListener { dismiss() }
    }


    companion object {
        private const val TAG = "DIALOG"
        private const val PERSON = "PERSON"
        fun show(person: Person, fragmentManager: FragmentManager) {
            if (fragmentManager.findFragmentByTag(TAG) == null)
                BottomSheetDialog().apply {
                    arguments = Bundle().apply {
                        putParcelable(PERSON, person)
                    }
                }
                    .show(fragmentManager, TAG)
        }
    }
}