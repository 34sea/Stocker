package com.example.stocker.views.Entrar.Crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stocker.R
import com.example.stocker.databinding.ActivityHomePageBinding
import com.example.stocker.databinding.ActivityListarProdutosBinding
import com.example.stocker.views.Entrar.Clientes.clienteListar
import com.example.stocker.views.Entrar.Estatistica.Estatistica
import com.example.stocker.views.Entrar.Home.HomePage
import com.example.stocker.views.Entrar.Login
import com.example.stocker.views.Entrar.PerfilAdmin.perfilAdmin
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class ListarProdutos : AppCompatActivity() {

    private lateinit var binding: ActivityListarProdutosBinding

    private val db = FirebaseFirestore.getInstance()

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.casaPage.setOnClickListener{
            casa()
        }

        binding.produtosLista.setOnClickListener{
            listProduto()
        }

        binding.estatisticaList.setOnClickListener{
            estatisticaL()
        }

        binding.clienteList.setOnClickListener{
            clientes()
        }

        binding.perfilListar.setOnClickListener{
            perfil()
        }

        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val voltar = Intent(this, Login::class.java)
            startActivity(voltar)
            finish()
        }

        var documentList: List<DocumentSnapshot> = emptyList()
        loadFirstRecord()

        binding.btnNext.setOnClickListener {
            db.collection("Produtos").get()
                .addOnSuccessListener { documento ->
                    documentList = documento.documents

                    if (currentIndex >= 0 && currentIndex < documentList.size) {
                        val document = documentList[currentIndex]

                        val apelido = document.getString("categoria") ?: ""
                        val nome = document.getString("nome") ?: ""
                        val idade = document.getString("preco") ?: ""
                        binding.resultadoNome.text = nome
                        binding.resultadoApelido.text = apelido
                        binding.resultadoIdade.text = idade

                        currentIndex++
                        binding.cada.text = (currentIndex).toString()

                    }

                    // Verifica se está no fim da lista
                    if (currentIndex == documentList.size) {
                        binding.cada.text = binding.todos.text.toString()
                        Snackbar.make(binding.root, "Fim da lista. Não é possível avançar.", Snackbar.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle errors
                }
        }

        binding.btnPrev.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--

                if (currentIndex >= 0 && currentIndex < documentList.size) {
                    val document = documentList[currentIndex]

                    val apelido = document.getString("categoria") ?: ""
                    val nome = document.getString("nome") ?: ""
                    val idade = document.getString("preco") ?: ""
                    binding.resultadoNome.text = nome
                    binding.resultadoApelido.text = apelido
                    binding.resultadoIdade.text = idade
                    binding.cada.text = (currentIndex+1).toString()
                }

                // Verifica se está no início da lista
                if (currentIndex == 0) {
                    Snackbar.make(binding.root, "Início da lista. Não é possível voltar.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun listProduto(){
        val lisProduto = Intent(this, ListarProdutos::class.java)
        startActivity(lisProduto)
    }

    private fun casa(){
        val casaT = Intent(this, HomePage::class.java)
        startActivity(casaT)
    }

    private fun estatisticaL(){
        val estatisticaT = Intent(this, Estatistica::class.java)
        startActivity(estatisticaT)
    }

    private fun clientes(){
        val clienteT = Intent(this, clienteListar::class.java)
        startActivity(clienteT)
    }

    private fun perfil(){
        val perfilT = Intent(this, perfilAdmin::class.java)
        startActivity(perfilT)
    }

    private fun loadFirstRecord() {
        db.collection("Produtos").get()
            .addOnSuccessListener { documento ->
                val documentList = documento.documents

                if (documentList.isNotEmpty()) {
                    val firstDocument = documentList.first()

                    val apelido = firstDocument.getString("categoria") ?: ""
                    val nome = firstDocument.getString("nome") ?: ""
                    val idade = firstDocument.getString("preco") ?: " "
                    binding.resultadoNome.text = nome
                    binding.resultadoApelido.text = apelido
                    binding.resultadoIdade.text = idade

                    currentIndex = 0
                    total()
                    binding.cada.text = (currentIndex+1).toString()
                }
            }
            .addOnFailureListener { exception ->
                // Handle errors
            }
    }

    private fun total(){
        db.collection("Produtos")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val numeroTotalRegistros = querySnapshot.size()
                binding.todos.text = numeroTotalRegistros.toString()
            }
            .addOnFailureListener { exception ->
            }
    }
}