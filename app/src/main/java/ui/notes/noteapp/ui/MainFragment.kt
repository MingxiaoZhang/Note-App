package ui.notes.noteapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ui.notes.noteapp.R
import ui.notes.noteapp.viewmodel.NotesViewModel

class MainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.main_page, container, false)
        val model : NotesViewModel by activityViewModels { NotesViewModel.Factory }
        model.getNotes().observe(viewLifecycleOwner) {
            update(model, view)
        }
        view.findViewById<EditText>(R.id.searchText).addTextChangedListener(
            afterTextChanged = {
                model.setSearchText(it.toString())
                model.filterNotes()
            }
        )
        view.findViewById<Switch>(R.id.archiveSwitch).setOnCheckedChangeListener{
                _, isChecked ->
            model.setViewArchived(isChecked)
            update(model, view)
        }
        view.findViewById<FloatingActionButton>(R.id.addBtn).setOnClickListener {
            findNavController().navigate(R.id.mainToAdd)
        }
        return view
    }

    fun update(model:NotesViewModel, view:View) {
        view.findViewById<Switch>(R.id.archiveSwitch).isChecked = model.getViewArchived().value == true
        val layout = view.findViewById<LinearLayout>(R.id.notesView)
        layout.removeAllViews()
        model.getNotes().value?.forEach { note ->
            // Using the layoutInflator to generate a small "sub-scene-graph" out of string_view.xml.
            //   The layoutInflator returns a View, which is than added to the LinearLayout in activity_main.xml.
            //   For this, the LinearLayout has to have an id. (Usually, ViewGroup are not given an id because it is rarely needed.)
                layoutInflater.inflate(R.layout.note_view, null, false).apply {
                    if (note.value?.important == true) {
                        setBackgroundColor(Color.YELLOW)
                    } else if (note.value?.archived == true) {
                        setBackgroundColor(Color.GRAY)
                    }
                    findViewById<TextView>(R.id.noteTitle).text = note.value?.title
                    findViewById<TextView>(R.id.noteContent).text = note.value?.content
                    findViewById<Button>(R.id.archiveButton).setOnClickListener {
                        note.value?.let { it1 -> model.setNoteArchived(it1.id, !note.value!!.archived) }
                    }
                    findViewById<Button>(R.id.deleteButton).setOnClickListener {
                        note.value?.let { it1 -> model.deleteNote(it1.id) }
                    }
                    setOnClickListener {
                        note.value?.let { it1 -> println(it1.id) }
                        note.value?.let { it1 -> model.setEditNote(it1) }
                        findNavController().navigate(R.id.mainToEdit)
                    }
                    layout.addView(this)
            }
        }
    }
}