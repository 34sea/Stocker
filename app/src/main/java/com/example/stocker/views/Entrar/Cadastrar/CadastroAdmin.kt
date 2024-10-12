package com.example.stocker.views.Entrar.Cadastrar

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stocker.R
import com.example.stocker.databinding.ActivityCadastroAdminBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class CadastroAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroAdminBinding

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCadatroAdmin.setOnClickListener{view->
            val email = binding.Editemail.text.toString()
            val senha = binding.Editsenha.text.toString()
            if(email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view,"Preencha todos os campos", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{
                auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener{ cadastro ->
                    if(cadastro.isSuccessful){
                        val snackbar = Snackbar.make(view,"Cadastrado com sucesso", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                        binding.Editemail.setText("")
                        binding.Editsenha.setText("")
                    }
                }.addOnFailureListener{exception ->
                    val mensagemErro = when(exception){
                        is FirebaseAuthWeakPasswordException -> "A senha deve ter pelo menos 6 caracteres"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um email valido"
                        is FirebaseAuthUserCollisionException -> "O usuário já existe!!!"
                        is FirebaseNetworkException -> "Sem internet!!!"
                        else -> "Erro ao cadastrar"
                    }
                    val snackbar = Snackbar.make(view,mensagemErro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.BLUE)
                    snackbar.show()
                }
            }
        }

    }
}