package com.mikeherasimov.doitapp.ui.mytasks

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.mikeherasimov.doitapp.R
import com.mikeherasimov.doitapp.ui.util.getSelectedRadioButtonTitle

class SortTasksDialog: DialogFragment() {

    private lateinit var listener: SortingOrderListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = targetFragment as SortingOrderListener
        } catch (e: ClassCastException) {
            throw ClassCastException(targetFragment.toString()
                    + " must implement SortingOrderListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val contentView = activity!!.layoutInflater.inflate(R.layout.dialog_sorting_order, null)
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle(R.string.dialog_title_sorting_order)
            .setView(contentView)
            .setPositiveButton(R.string.dialog_btn_sort) { _, _ ->
                val sortBy = contentView.findViewById<RadioGroup>(R.id.rgSortBy).getSelectedRadioButtonTitle()
                val order = contentView.findViewById<RadioGroup>(R.id.rgOrder).getSelectedRadioButtonTitle()
                listener.onSortingOrderPicked("$sortBy $order")
            }
        return builder.create()
    }

    interface SortingOrderListener {
        fun onSortingOrderPicked(sortOrder: String)
    }

}