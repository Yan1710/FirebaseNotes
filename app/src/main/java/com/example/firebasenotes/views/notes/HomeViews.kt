package com.example.firebasenotes.views.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.navigation.NavController
import com.example.firebasenotes.viewModels.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, noteVM: NotesViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Mis Notas") }, navigationIcon = {
                IconButton(onClick = {
                    noteVM.sinOut()
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "")
                }
            },
                actions = {   IconButton(onClick = {
                 navController.navigate("AddNoteView")
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }
                }

            )
        }


    ) { pad ->
        Column(
            modifier = Modifier.padding(pad),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

        }

    }
}