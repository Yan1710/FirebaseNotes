package com.example.firebasenotes.viewModels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class NotesViewModel : ViewModel() {


    private val auth: FirebaseAuth = Firebase.auth

    fun sinOut() {
        auth.signOut()
    }


}