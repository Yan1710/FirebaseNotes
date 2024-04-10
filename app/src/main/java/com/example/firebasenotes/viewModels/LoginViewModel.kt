package com.example.firebasenotes.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LoginViewModel:ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    var showAlert by mutableStateOf(false)
    
    fun login(email:String,password:String,onSuccess:() -> Unit){
        viewModelScope.launch {
            try {
              auth.signInWithEmailAndPassword(email,password)
                  .addOnCompleteListener{ task ->
                      if(task.isSuccessful){
                      onSuccess()
                      }else{
                          Log.d("ERORR", "login: Usario ")
                          showAlert = true
                      }

                  }
            }catch (e:Exception) {
                Log.d("ERROR",e.message.toString())
            }
        }
    }
    fun closeAlert() {
        showAlert = false
    }




    fun createUser(email:String,password:String,username: String,onSuccess:() -> Unit){
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){
                            saveuser(username)
                            onSuccess()
                        }else{
                            Log.d("ERORR", "error al crear el usuario ")
                            showAlert = true
                        }

                    }
            }catch (e:Exception) {
                Log.d("ERROR",e.message.toString())
            }
        }
    }

    private fun saveuser(username:String){
        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email

        val user = UserModel(
            userId = id.toString(),
            email = email.toString(),
            username = username
        )

        FirebaseFirestore.getInstance().collection("Users").add(user).addOnSuccessListener{
            Log.d("GUARDo", "saveuser: ")
        }.addOnFailureListener{
            Log.d("ERROR al GUARDAR", "saveuser: ")
        }
    }




}