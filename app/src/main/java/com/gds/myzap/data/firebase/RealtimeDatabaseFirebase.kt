package com.gds.myzap.data.firebase

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.gds.myzap.R
import com.gds.myzap.data.model.Usuario
import kotlinx.coroutines.launch

object RealtimeDatabaseFirebase {
    internal val db by lazy { ConfigFirebase.getDatabasebFirebase() }

    suspend fun salvarDadosDoCadastro(usuario: Usuario){
        db.child("Usuarios")
            .child(UsuarioFirebase.userKey())
            .setValue(usuario)
    }
    suspend fun recuperarDadosDoBD(){
        db.child("Usuarios").child(UsuarioFirebase.userKey()).get().addOnCompleteListener {task->
            val users = ArrayList<Usuario>()
            val result = task.result
            result?.let {
                val item = it.getValue(Usuario::class.java)!!
                users.add(item)
            }
        }
    }

    suspend fun recuperandoDadosDoUser(context: Context,imgView : ImageView,textNome : TextView) {
        val usuario = UsuarioFirebase.currentUser()
        val url = usuario?.photoUrl
        if (url != null) {
            Glide
                .with(context)
                .load(url)
                .into(imgView)
        } else {
            imgView.setImageResource(R.drawable.padrao)
        }
        textNome.setText(usuario?.displayName ?: "Sem Nome")
    }

}