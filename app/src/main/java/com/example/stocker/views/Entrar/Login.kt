package com.example.stocker.views.Entrar

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.stocker.R
import com.example.stocker.databinding.ActivityLoginBinding
import com.example.stocker.views.Entrar.Home.HomePage
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEntrar.setOnClickListener { view ->
            val email = binding.Editemail.text.toString()
            val senha = binding.Editsenha.text.toString()
            if (email.isEmpty() || senha.isEmpty()) {
                val snackbar = Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener{ autenticacao ->
                    if(autenticacao.isSuccessful){
                        telaPrincipal()
                    }
                }.addOnFailureListener{exception ->
                    val mensagemErro = when(exception){
                        is FirebaseAuthWeakPasswordException -> "A senha deve ter pelo menos 8 caracteres"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um email valido"
                        is FirebaseNetworkException -> "Sem internet!!!"
                        else -> "Erro no login"
                    }
                    val snackbar = Snackbar.make(view,mensagemErro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }

            }
        }
    }

    private fun telaPrincipal(){
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }

    override fun onStart(){
        super.onStart()
        val usuarioAtual = FirebaseAuth.getInstance().currentUser
        if(usuarioAtual!=null){
            telaPrincipal()
        }
    }
}