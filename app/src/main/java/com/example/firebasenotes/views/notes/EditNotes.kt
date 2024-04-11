package com.example.firebasenotes.views.notes

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.firebasenotes.viewModels.NotesViewModel

@Composable
fun EditNotesView(navController: NavController,notesVM: NotesViewModel,idDoc:String){
Text(text = idDoc)
}