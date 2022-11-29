package com.fime.roomier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView

class primera_perfil : AppCompatActivity() {
    val TAG:String = "primera_perfil"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primera_perfil)

        //Para que vaya de la primer pantalla de inicio -> segunda pantalla de inicio "perfil"
        val btn_editarP: Button = findViewById(R.id.edit_pp)
        btn_editarP.setOnClickListener {

            val intent: Intent = Intent(this, segunda_perfil::class.java)
            startActivity(intent)
        }

        //Para que cuando el usuario haga clic en el icono de foco, lo lleve a la pantalla de consejos
        val btn_icon_consejo: ImageView = findViewById(R.id.icono_consejos2)
        btn_icon_consejo.setOnClickListener {

            val intent: Intent = Intent(this, consejos_pp::class.java)
            startActivity(intent)
        }

        val btn_iconoMapa: ImageView = findViewById(R.id.icon_mapa3)
        btn_iconoMapa.setOnClickListener{
            Log.d(TAG, "onCreate: Haciendo Click")
            val intent: Intent = Intent(this, Mapa::class.java)
            startActivity(intent)
        }
    }
}