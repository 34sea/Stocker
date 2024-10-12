package com.example.stocker.views.Entrar.Estatistica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stocker.R
import com.example.stocker.databinding.ActivityEstatisticaBinding
import com.example.stocker.databinding.ActivityListarProdutosBinding
import com.example.stocker.views.Entrar.Clientes.clienteListar
import com.example.stocker.views.Entrar.Crud.ListarProdutos
import com.example.stocker.views.Entrar.Home.HomePage
import com.example.stocker.views.Entrar.Login
import com.example.stocker.views.Entrar.PerfilAdmin.perfilAdmin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Estatistica : AppCompatActivity() {

    private lateinit var binding: ActivityEstatisticaBinding

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEstatisticaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        totalPromoccoes()
        totalProdutos()
        totalVendas()

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

        binding.detalhesProdutos.setOnClickListener {
            listProdutos()
        }

        binding.detalhesPromo.setOnClickListener {
            listPromo()
        }

        binding.detalhesVendas.setOnClickListener {
            listVendas()
        }
    }

    private fun listProdutos(){
        val lisProduto = Intent(this, ListarProdutos::class.java)
        startActivity(lisProduto)
    }

    private fun listPromo(){
        val lisProduto = Intent(this, detalhesPromo::class.java)
        startActivity(lisProduto)
    }

    private fun listVendas(){
        val lisProduto = Intent(this, detalhesVenda::class.java)
        startActivity(lisProduto)
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

    private fun totalProdutos(){
        db.collection("Produtos")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val numeroTotalRegistros = querySnapshot.size()
                binding.quantiProduto.text = numeroTotalRegistros.toString()
            }
            .addOnFailureListener { exception ->
            }
    }

    private fun totalPromoccoes(){
        db.collection("Promoccoes")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val numeroTotalRegistros = querySnapshot.size()
                binding.quantiPromo.text = numeroTotalRegistros.toString()
            }
            .addOnFailureListener { exception ->
            }
    }

    private fun totalVendas(){
        db.collection("Vendas")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val numeroTotalRegistros = querySnapshot.size()
                binding.quantiVendas.text = numeroTotalRegistros.toString()
            }
            .addOnFailureListener { exception ->
            }
    }
}