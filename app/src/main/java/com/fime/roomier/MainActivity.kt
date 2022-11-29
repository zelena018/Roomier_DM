package com.fime.roomier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    val TAG:String = "main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Para hacer clic en boton "guardar" y regresar a menu "editar perfil"
        val btn_guardarCambios: Button = findViewById(R.id.btn_guardarMostrar)


        btn_guardarCambios.setOnClickListener {

            val intent: Intent = Intent(this, segunda_perfil::class.java)
            startActivity(intent)
        }

        //Para que cuando el usuario haga clic en el icono de foco, lo lleve a la pantalla de consejos
        val btn_icon_consejo3: ImageView = findViewById(R.id.icono_consejos)
        btn_icon_consejo3.setOnClickListener {

            val intent: Intent = Intent(this, consejos_pp::class.java)
            startActivity(intent)
        }

        //Para que cuando el usuario haga clic en el icono "perfil", estando en la pantalla 3 (Donde estan las casillas por seleccionar
        //si quiere mostrar cierta información en su perfil, se pueda regresar a la primer pantalla de "perfil"
        val btn_icon_PP2: ImageView = findViewById(R.id.icono_perfil)
        btn_icon_PP2.setOnClickListener {

            val intent: Intent = Intent(this, primera_perfil::class.java)
            startActivity(intent)
        }

        val btn_iconoMapa: ImageView = findViewById(R.id.icon_mapa)
        btn_iconoMapa.setOnClickListener{
            Log.d(TAG, "onCreate: Haciendo Click")
            val intent: Intent = Intent(this, Mapa::class.java)
            startActivity(intent)
        }


    }
}