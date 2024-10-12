package com.example.stocker.views.Entrar.Crud

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stocker.R
import com.example.stocker.databinding.ActivityAdcionarPromocoesBinding
import com.example.stocker.databinding.ActivityCadastroBinding
import com.example.stocker.views.Entrar.Clientes.clienteListar
import com.example.stocker.views.Entrar.Home.HomePage
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class AdcionarPromocoes : AppCompatActivity() {

    private lateinit var binding: ActivityAdcionarPromocoesBinding

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdcionarPromocoesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCadastrarProduto.setOnClickListener{m->
            val idUsuario = db.collection("Promoccoes").document().id
            val nome = binding.editNome.text.toString()
            val categoria = binding.editCategoria.text.toString()
            val preco = binding.editPreco.text.toString()


            if(nome.isEmpty() || categoria.isEmpty() || preco.isEmpty()){
                val snackbar = Snackbar.make(m, "Preencha todos os campos", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()

            }else{
                val usuariosMap = hashMapOf(
                    "nome" to nome,
                    "categoria" to categoria,
                    "preco" to preco
                )
                db.collection("Promoccoes").document(idUsuario)
                    .set(usuariosMap).addOnCompleteListener{
                        telaPrincipal()
                    }.addOnFailureListener{
                        val snackbar = Snackbar.make(m, "Falha", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                    }
            }
        }
    }

    private fun telaPrincipal(){
        val perfilT = Intent(this, HomePage::class.java)
        startActivity(perfilT)
    }
}
