package com.favorezapp.goodfood.favoritesrecipes.presentation

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.favorezapp.goodfood.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DeleteAllDialog: DialogFragment() {
    private lateinit var listener: Listener

    interface Listener {
        fun deleteAllFavorites()
    }

    fun setListener(listener: Listener): DeleteAllDialog {
        this.listener = listener
        return this
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialog_AppCompat)
            .setTitle(R.string.confirmation)
            .setMessage(R.string.delete_all_favs_question)
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton(R.string.yes) { _, _ ->
                listener.deleteAllFavorites()
            }
            .setNegativeButton(R.string.no) { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
    }
}