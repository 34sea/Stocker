package com.example.stocker.views.Entrar.Cadastrar

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stocker.R
import com.example.stocker.databinding.ActivityCadastroBinding
import com.example.stocker.views.Entrar.Clientes.clienteListar
import com.example.stocker.views.Entrar.PerfilAdmin.perfilAdmin
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class Cadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCadastrarDb.setOnClickListener{m->
            val idUsuario = db.collection("Usuarios").document().id
            val nome = binding.Editnome.text.toString()
            val apelido = binding.Editapelido.text.toString()
            val idade = binding.Editidade.text.toString()
            val senha = binding.Editsenha.text.toString()


            if(nome.isEmpty() || idade.isEmpty() || senha.isEmpty() || apelido.isEmpty()){
                val snackbar = Snackbar.make(m, "Preencha todos os campos", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()

            }else{
                val usuariosMap = hashMapOf(
                    "nome" to nome,
                    "apelido" to apelido,
                    "idade" to idade,
                    "senha" to senha
                )
                db.collection("Usuarios").document(idUsuario)
                    .set(usuariosMap).addOnCompleteListener{
                        telaCliente()
                    }.addOnFailureListener{
                        val snackbar = Snackbar.make(m, "Falha", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                    }
            }
        }
    }

    private fun telaCliente(){
        val perfilT = Intent(this, clienteListar::class.java)
        startActivity(perfilT)
    }
}