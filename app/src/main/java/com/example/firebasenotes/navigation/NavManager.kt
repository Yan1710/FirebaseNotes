package com.example.firebasenotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firebasenotes.viewModels.LoginViewModel
import com.example.firebasenotes.viewModels.NotesViewModel
import com.example.firebasenotes.views.login.BlankView
import com.example.firebasenotes.views.login.TabsView
import com.example.firebasenotes.views.notes.AddNoteView
import com.example.firebasenotes.views.notes.EditNotesView
import com.example.firebasenotes.views.notes.HomeView

@Composable
fun NavManager(loginVM: LoginViewModel, notesVM: NotesViewModel) {
    val navcController = rememberNavController()
    NavHost(navController = navcController, startDestination = "Blank") {
        composable("Blank") {
            BlankView(navcController)
        }
        composable("AddNoteView") {
            AddNoteView(navcController, notesVM)
        }
        composable("Login") {
            TabsView(navcController, loginVM)
        }
        composable("Home") {
            HomeView(navcController, notesVM)
        }
        composable("EditNote/{idDoc}", arguments = listOf(
            navArgument("idDoc"){type = NavType.StringType}
        )) {
            val idDoc = it.arguments?.getString("idDoc")?: ""
            EditNotesView(navcController, notesVM,idDoc)
        }


    }
}