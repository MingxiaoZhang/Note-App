package ui.notes.noteapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ui.notes.noteapp.R
import ui.notes.noteapp.viewmodel.NotesViewModel

class AddFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.add_page, container, false)
        val model : NotesViewModel by activityViewModels { NotesViewModel.Factory }
        view.findViewById<Button>(R.id.createButton).setOnClickListener {
            model.addNote(
                view.findViewById<EditText>(R.id.newNoteTitle).text.toString(),
                view.findViewById<EditText>(R.id.newNoteContent).text.toString(),
                view.findViewById<Switch>(R.id.importantSwitch).isChecked
            )
            println(view.findViewById<EditText>(R.id.newNoteTitle).text.toString())
            model.getNotes().value?.let { it1 -> println(it1.size) }
            findNavController().navigate(R.id.addToMain)
        }
        return view
    }
}