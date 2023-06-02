package ui.notes.noteapp

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import ui.notes.noteapp.viewmodel.NotesViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val model : NotesViewModel by viewModels { NotesViewModel.Factory }
        model.getNotes().observe(this) {
            Log.i("MainActivity", it?.fold("Visible Note IDs:") { acc, cur -> "$acc ${cur.value?.id}" } ?: "[ERROR]")
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                Log.i("OrientationChange", "Landscape")
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                Log.i("OrientationChange", "Portrait")
            }
            else -> {
                Log.i("OrientationChange", "Unidentified")
            }
        }
    }
}