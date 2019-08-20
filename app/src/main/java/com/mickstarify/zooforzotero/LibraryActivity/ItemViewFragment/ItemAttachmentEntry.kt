package com.mickstarify.zooforzotero.LibraryActivity.ItemViewFragment


import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import com.mickstarify.zooforzotero.R
import com.mickstarify.zooforzotero.ZoteroAPI.Model.Item

private const val ARG_ATTACHMENT = "attachment"


class ItemAttachmentEntry : Fragment() {
    private var attachment: Item? = null
    var fileOpenListener : OnAttachmentFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            attachment = it.getParcelable(ARG_ATTACHMENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_item_attachment_entry, container, false)
        val filename = view.findViewById<TextView>(R.id.textView_filename)
        filename.text = attachment?.data?.get("filename") ?:"unknown"

        val layout = view.findViewById<ConstraintLayout>(R.id.constraintLayout_attachments_entry)

        layout.setOnClickListener{
            fileOpenListener?.openAttachmentFileListener(attachment!!)
        }

        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAttachmentFragmentInteractionListener) {
            fileOpenListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnAttachmentFragmentInteractionListener")
        }
    }
    interface OnAttachmentFragmentInteractionListener {
        fun openAttachmentFileListener(item: Item)
    }
    companion object {
        @JvmStatic
        fun newInstance(attachment : Item) =
            ItemAttachmentEntry().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_ATTACHMENT, attachment)
                }
            }
    }
}