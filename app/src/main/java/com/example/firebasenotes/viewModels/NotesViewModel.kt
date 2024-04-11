package com.example.firebasenotes.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.model.NotesState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class NotesViewModel : ViewModel() {


    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    private val _notesData = MutableStateFlow<List<NotesState>>(emptyList())
    val notesData: StateFlow<List<NotesState>> = _notesData

    var state by mutableStateOf(NotesState())
        private set

    fun onValue(value: String, text: String) {
        when (text) {
            "title" -> state = state.copy(title = value)
            "note" -> state = state.copy(note = value)
        }
    }


    fun fetchNotes() {
        val email = auth.currentUser?.email
        firestore.collection("Notes").whereEqualTo("emailuser", email.toString())
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val documents = mutableListOf<NotesState>()
                if (querySnapshot != null) {
                    for (document in querySnapshot) {
                        val myDocument =
                            document.toObject(NotesState::class.java).copy(idDoc = document.id)
                        documents.add(myDocument)
                    }
                }
                _notesData.value = documents
            }
    }

    fun saveNewNote(title: String, note: String, onSuccess: () -> Unit) {
        val email = auth.currentUser?.email
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newNote = hashMapOf(
                    "title" to title,
                    "note" to note,
                    "date" to formateDate(),
                    "emailuser" to email.toString()
                )
                firestore.collection("Notes").add(newNote)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener {
                        Log.d("FALLO", "saveNewNote: al guardar")
                    }
            } catch (e: Exception) {
                Log.d("GUARDADO FALLO", "saveNewNote: " + e.message)
            }

        }
    }


    fun editNote(iDoc: String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val editNote = hashMapOf(
                    "title" to state.title,
                    "note" to state.note,

                    )
                firestore.collection("Notes").document(iDoc)
                    .update(editNote as Map<String, Any>)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener {
                        Log.d("FALLO", "saveNewNote: al editar")
                    }
            } catch (e: Exception) {
                Log.d("Editar FALLO", "saveNewNote: " + e.message)
            }

        }
    }


    fun deleteNote(iDoc: String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                firestore.collection("Notes").document(iDoc)
                    .delete()
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener {
                        Log.d("FALLO", "saveNewNote: al delete")
                    }
            } catch (e: Exception) {
                Log.d("Delete FALLO", "saveNewNote: " + e.message)
            }

        }
    }

    private fun formateDate(): String {
        val currentDate: Date = Calendar.getInstance().time
        val res = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return res.format(currentDate)

    }

    fun getNotById(documentId: String) {
        firestore.collection("Notes").document(documentId).addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val note = snapshot.toObject(NotesState::class.java)
                state = state.copy(
                    title = note?.title ?: "",
                    note = note?.note ?: ""
                )
            }
        }
    }

    fun sinOut() {
        auth.signOut()
    }


}