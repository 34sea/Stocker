package com.example.stocker.views.Entrar.PerfilAdmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stocker.R
import com.example.stocker.databinding.ActivityListarProdutosBinding
import com.example.stocker.databinding.ActivityPerfilAdminBinding
import com.example.stocker.views.Entrar.Cadastrar.CadastroAdmin
import com.example.stocker.views.Entrar.Clientes.ClientePage
import com.example.stocker.views.Entrar.Clientes.clienteListar
import com.example.stocker.views.Entrar.Crud.ApagarPromo
import com.example.stocker.views.Entrar.Crud.ListarProdutos
import com.example.stocker.views.Entrar.Crud.atualizarPromo
import com.example.stocker.views.Entrar.Estatistica.Estatistica
import com.example.stocker.views.Entrar.Home.HomePage
import com.example.stocker.views.Entrar.Login
import com.google.firebase.auth.FirebaseAuth

class perfilAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdminCliente.setOnClickListener{
            telaPro()
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

        binding.btnNovoAdmin.setOnClickListener {
            addAdmin()
        }

        binding.btnAdminPromoA.setOnClickListener {
            updatePromo()
        }

        binding.btnAdminPromoAp.setOnClickListener {
            delPromo()
        }

        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val voltar = Intent(this, Login::class.java)
            startActivity(voltar)
            finish()
        }
    }

    private fun listProduto(){
        val lisProduto = Intent(this, ListarProdutos::class.java)
        startActivity(lisProduto)
    }

    private fun updatePromo(){
        val lisProduto = Intent(this, atualizarPromo::class.java)
        startActivity(lisProduto)
    }

    private fun delPromo(){
        val lisProduto = Intent(this, ApagarPromo::class.java)
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

    private fun addAdmin(){
        val adminT = Intent(this, CadastroAdmin::class.java)
        startActivity(adminT)
    }

    private fun telaPro(){
        val perfilT = Intent(this, ClientePage::class.java)
        startActivity(perfilT)
    }

}
