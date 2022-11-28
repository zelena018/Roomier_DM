package com.fime.roomier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class consejos_pp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consejos_pp)

        //Para que cuando el usuario haga clic en el icono de "perfil", lo lleve a la pantalla principal de "perfil"
        val btn_iconPP_consejo: ImageView = findViewById(R.id.icono_perfil4)
        btn_iconPP_consejo.setOnClickListener {

            val intent: Intent = Intent(this, primera_perfil::class.java)
            startActivity(intent)
        }
    }
}