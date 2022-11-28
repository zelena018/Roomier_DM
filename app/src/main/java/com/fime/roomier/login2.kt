package com.fime.roomier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class login2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)


        val btn_registrarsePV: Button = findViewById(R.id.btn_registro)
        btn_registrarsePV.setOnClickListener {

            val intent: Intent = Intent(this, primera_perfil::class.java)
            startActivity(intent)
        }

        val btn_regresarIS: Button = findViewById(R.id.btn_regr_IS)
        btn_regresarIS.setOnClickListener {

            val intent: Intent = Intent(this, login1::class.java)
            startActivity(intent)
        }
    }
}