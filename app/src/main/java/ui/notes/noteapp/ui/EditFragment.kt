package ui.notes.noteapp.ui

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ui.notes.noteapp.R
import ui.notes.noteapp.viewmodel.NotesViewModel

class EditFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.edit_page, container, false)
        val model : NotesViewModel by activityViewModels { NotesViewModel.Factory }
        view.findViewById<EditText>(R.id.titleEdit).text = Editable.Factory.getInstance().newEditable(model.getEditNote().title)
        view.findViewById<EditText>(R.id.contentEdit).text = Editable.Factory.getInstance().newEditable(model.getEditNote().content)
        view.findViewById<Switch>(R.id.importantEdit).isChecked = model.getEditNote().important
        view.findViewById<Switch>(R.id.archiveEdit).isChecked = model.getEditNote().archived
        view.findViewById<Switch>(R.id.importantEdit).setOnCheckedChangeListener{
            _,isChecked -> model.setNoteImportant(model.getEditNote().id, isChecked)
            if (view.findViewById<Switch>(R.id.archiveEdit).isChecked && isChecked) {
                view.findViewById<Switch>(R.id.archiveEdit).isChecked = false
            }
        }
        view.findViewById<Switch>(R.id.archiveEdit).setOnCheckedChangeListener {
            _,isChecked -> model.setNoteArchived(model.getEditNote().id, isChecked)
            if (view.findViewById<Switch>(R.id.importantEdit).isChecked && isChecked) {
                view.findViewById<Switch>(R.id.importantEdit).isChecked = false
            }
        }
        view.findViewById<EditText>(R.id.titleEdit).addTextChangedListener(
            afterTextChanged = {
                model.setNoteTitle(model.getEditNote().id,
                    view.findViewById<EditText>(R.id.titleEdit).text.toString())
            }
        )
        view.findViewById<EditText>(R.id.contentEdit).addTextChangedListener(
            afterTextChanged = {
                model.setNoteContent(model.getEditNote().id,
                    view.findViewById<EditText>(R.id.contentEdit).text.toString())
            }
        )
        return view
    }
}