package com.example.stocker.views.Entrar.Crud

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stocker.R
import com.example.stocker.databinding.ActivityAtualizarProdutosBinding
import com.example.stocker.databinding.ActivityCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class AtualizarProdutos : AppCompatActivity() {

    private lateinit var binding: ActivityAtualizarProdutosBinding

    private val db = FirebaseFirestore.getInstance()

    private var currentIndex = 0

    private var idProduto= " "

    private var nomeProdutosUpdate = " "

    private var categoriaProdutoUpdate = " "

    private var precoProdutoUpdate = " "

    private var tamanhoProdutos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAtualizarProduto.setOnClickListener{atualizado ->


            if(idProduto.isEmpty()){
                val snackbar = Snackbar.make(
                    atualizado,
                    "Sem dados",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setBackgroundTint(Color.BLACK)
                snackbar.show()
            }else {
                nomeProdutosUpdate = if(!binding.editNome.text.toString().isEmpty())binding.editNome.text.toString() else nomeProdutosUpdate
                categoriaProdutoUpdate = if(!binding.editCategoria.text.toString().isEmpty())binding.editCategoria.text.toString() else categoriaProdutoUpdate
                precoProdutoUpdate = if(!binding.editPreco.text.toString().isEmpty())binding.editPreco.text.toString() else precoProdutoUpdate

                val produtosMap = hashMapOf<String, Any>(
                    "nome" to nomeProdutosUpdate,
                    "categoria" to categoriaProdutoUpdate,
                    "preco" to precoProdutoUpdate
                )
                //binding.testess.text = binding.resultadoNome.text.toString()
                db.collection("Produtos").document(idProduto)
                    .update(produtosMap).addOnCompleteListener {
                        val snackbar = Snackbar.make(
                            atualizado,
                            "Dados atualizados com sucesso",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.setBackgroundTint(Color.BLUE)
                        snackbar.show()
                        telaPrincipal()
                    }
            }

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
                        updateVariablesFromDocument(documentList[currentIndex])
                        binding.cada.text = (currentIndex).toString()
                    }

                    // Verifica se está no fim da lista
                    if (currentIndex == (documentList.size)) {
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
                    updateVariablesFromDocument(documentList[currentIndex])
                    binding.cada.text = (currentIndex+1).toString()
                }

                // Verifica se está no início da lista
                if (currentIndex == 0) {
                    binding.cada.text = binding.todos.text.toString()
                    Snackbar.make(binding.root, "Início da lista. Não é possível voltar.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
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
                    currentIndex = 1

                }
            }
            .addOnFailureListener { exception ->
                // Handle errors
            }
    }

    private fun updateVariablesFromDocument(document: DocumentSnapshot) {
        idProduto = document.id
        nomeProdutosUpdate = document.getString("nome") ?: ""
        categoriaProdutoUpdate = document.getString("categoria") ?: ""
        precoProdutoUpdate = document.getString("preco") ?: ""

        binding.resultadoNome.text = nomeProdutosUpdate
        binding.resultadoApelido.text = categoriaProdutoUpdate
        binding.resultadoIdade.text = precoProdutoUpdate

    }

    private fun telaPrincipal(){
        val perfilT = Intent(this, ListarProdutos::class.java)
        startActivity(perfilT)
    }

    private fun total(){
        db.collection("Produtos")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val numeroTotalRegistros = querySnapshot.size()
                tamanhoProdutos = numeroTotalRegistros
                binding.todos.text = numeroTotalRegistros.toString()
            }
            .addOnFailureListener { exception ->
            }
    }

}