package com.example.stocker.views.Entrar.Clientes

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.stocker.R
import com.example.stocker.databinding.ActivityClientePageBinding
import com.example.stocker.databinding.ActivityListarProdutosBinding
import com.example.stocker.views.Entrar.Crud.ListarProdutos
import com.example.stocker.views.Entrar.Estatistica.Estatistica
import com.example.stocker.views.Entrar.Home.HomePage
import com.example.stocker.views.Entrar.Login
import com.example.stocker.views.Entrar.PerfilAdmin.perfilAdmin
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ClientePage : AppCompatActivity() {
    private lateinit var binding: ActivityClientePageBinding

    private val db = FirebaseFirestore.getInstance()

    private var currentIndex = 0

    private var idProduto= " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val voltar = Intent(this, Login::class.java)
            startActivity(voltar)
            finish()
        }

        binding.btnClientePage.setOnClickListener {
            telaPro()
        }

        binding.btnPromo.setOnClickListener {
            telaP()

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
                        binding.resultadoCate.text = apelido
                        binding.resultadoPreco.text = idade

                        currentIndex++
                        binding.cada.text = (currentIndex).toString()
                        updateVariablesFromDocument(documentList[currentIndex])

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
                    binding.resultadoCate.text = apelido
                    binding.resultadoPreco.text = idade
                    updateVariablesFromDocument(documentList[currentIndex])
                    binding.cada.text = (currentIndex+1).toString()
                }

                // Verifica se está no início da lista
                if (currentIndex == 0) {
                    Snackbar.make(binding.root, "Início da lista. Não é possível voltar.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnClientePageCompra.setOnClickListener {comprar ->
            if(idProduto.isEmpty()){
                val snackbar = Snackbar.make(
                    comprar,
                    "Sem dados",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setBackgroundTint(Color.BLACK)
                snackbar.show()
            }else {
                val idVendas = db.collection("Vendas").document().id
                val nome = binding.resultadoNome.text.toString()
                val categoria = binding.resultadoCate.text.toString()
                val preco = binding.resultadoPreco.text.toString()
                val dataAtual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

                    val usuariosMap = hashMapOf(
                        "nome" to nome,
                        "categoria" to categoria,
                        "preco" to preco,
                        "data" to dataAtual.toString()
                    )
                    db.collection("Vendas").document(idVendas)
                        .set(usuariosMap).addOnCompleteListener{
                            db.collection("Produtos").document(idProduto)
                                .delete().addOnCompleteListener {
                                    telaPro()
                                }
                        }.addOnFailureListener{
                            val snackbar = Snackbar.make(comprar, "Falha", Snackbar.LENGTH_SHORT)
                            snackbar.setBackgroundTint(Color.RED)
                            snackbar.show()
                        }
            }
                //binding.testess.text = binding.resultadoNome.text.toString()

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
                    binding.resultadoCate.text = apelido
                    binding.resultadoPreco.text = idade

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

    private fun telaP(){
        val perfilT = Intent(this, Promoccao::class.java)
        startActivity(perfilT)
    }

    private fun telaPro(){
        val perfilT = Intent(this, ClientePage::class.java)
        startActivity(perfilT)
    }

    private fun updateVariablesFromDocument(document: DocumentSnapshot) {
        idProduto = document.id

    }
}