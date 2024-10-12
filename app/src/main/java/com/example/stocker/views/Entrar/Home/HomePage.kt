package com.example.stocker.views.Entrar.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stocker.R
import com.example.stocker.databinding.ActivityHomePageBinding
import com.example.stocker.views.Entrar.Clientes.clienteListar
import com.example.stocker.views.Entrar.Crud.AdcionarPromocoes
import com.example.stocker.views.Entrar.Crud.AdicionarProduto
import com.example.stocker.views.Entrar.Crud.ApagarProdutos
import com.example.stocker.views.Entrar.Crud.AtualizarProdutos
import com.example.stocker.views.Entrar.Crud.ListarProdutos
import com.example.stocker.views.Entrar.Estatistica.Estatistica
import com.example.stocker.views.Entrar.Login
import com.example.stocker.views.Entrar.PerfilAdmin.perfilAdmin
import com.google.firebase.auth.FirebaseAuth

class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.novoPost.setOnClickListener{
            addProdutos()
        }

        binding.produtosAtualizar.setOnClickListener{
            updadeProduto()
        }

        binding.produtosPromocoes.setOnClickListener{
            addPromocoes()
        }

       binding.produtoApagar.setOnClickListener{
           delProduto()
       }

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
    }

    private fun addProdutos(){
        val produtoNovo = Intent(this, AdicionarProduto::class.java)
        startActivity(produtoNovo)
    }

    private fun addPromocoes(){
        val promocaoNovo = Intent(this, AdcionarPromocoes::class.java)
        startActivity(promocaoNovo)
    }

    private fun updadeProduto(){
        val atuaProduto = Intent(this, AtualizarProdutos::class.java)
        startActivity(atuaProduto)
    }

    private fun listProduto(){
        val lisProduto = Intent(this, ListarProdutos::class.java)
        startActivity(lisProduto)
    }

    private fun delProduto(){
        val deleteProduto = Intent(this, ApagarProdutos::class.java)
        startActivity(deleteProduto)
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
}