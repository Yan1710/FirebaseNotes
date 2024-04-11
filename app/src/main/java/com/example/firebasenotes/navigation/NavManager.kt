package com.example.firebasenotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebasenotes.viewModels.LoginViewModel
import com.example.firebasenotes.viewModels.NotesViewModel
import com.example.firebasenotes.views.login.TabsView
import com.example.firebasenotes.views.notes.HomeView

@Composable
fun NavManager(loginVM:LoginViewModel,notesVM:NotesViewModel){
val navcController = rememberNavController()
    NavHost(navController = navcController, startDestination = "Login"){
        composable("Login"){
            TabsView(navcController,loginVM)
        }
        composable("Home"){
            HomeView(navcController,notesVM)
        }
    }
}