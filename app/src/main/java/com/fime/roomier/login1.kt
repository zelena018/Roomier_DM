package com.fime.roomier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class login1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login1)


        //Para ir de la primer pantalla de login -> Home
        val btn_iniciarS: Button = findViewById(R.id.btn_IS)
        btn_iniciarS.setOnClickListener {

            val intent: Intent = Intent(this, primera_perfil::class.java)
            startActivity(intent)
        }

        val btn_reg: Button = findViewById(R.id.btn_registrate_prim_vez)
        btn_reg.setOnClickListener {

            val intent: Intent = Intent(this, login2::class.java)
            startActivity(intent)
        }
    }
}