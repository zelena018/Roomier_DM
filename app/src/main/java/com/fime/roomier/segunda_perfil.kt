package com.fime.roomier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class segunda_perfil : AppCompatActivity() {

    //VARIABLES PARA LOS MENUS DESPLEGABLES
    //Trabajo
    private var sp_Trabajo:Spinner?=null
    private var selec_Trabajo:TextView?=null

    //Escuela
    private var sp_Estudio:Spinner?=null
    private var selec_Estudio:TextView?=null

    //Mascotas
    private var sp_Mascota:Spinner?=null
    private var selec_Mascota:TextView?=null

    //Relación
    private var sp_Relacion:Spinner?=null
    private var select_Relacion:TextView?=null

    //Bebidas
    private var sp_Beber:Spinner?=null
    private var selec_Beber:TextView?=null

    //Fumar
    private var sp_Fumar:Spinner?=null
    private var selec_Fumar:TextView?=null

    //Ruido
    private var sp_Ruido:Spinner?=null
    private var selec_Ruido:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda_perfil)

        //TRABAJO
        sp_Trabajo=findViewById(R.id.sp_trabajo)
        selec_Trabajo=findViewById(R.id.selec_trabajo)

        //ESCUELA
        sp_Estudio=findViewById(R.id.sp_estudio)
        selec_Estudio=findViewById(R.id.selec_estudio)

        //Mascotas
        sp_Mascota=findViewById(R.id.sp_mascota)
        selec_Mascota=findViewById(R.id.selec_mascota)

        //Relacion
        sp_Relacion=findViewById(R.id.sp_relacion)
        select_Relacion=findViewById(R.id.selec_relacion)

        //Bebidas
        sp_Beber=findViewById(R.id.sp_beber)
        selec_Beber=findViewById(R.id.selec_beber)

        //Fumar
        sp_Fumar=findViewById(R.id.sp_fumar)
        selec_Fumar=findViewById(R.id.selec_fumar)

        //Ruido
        sp_Ruido=findViewById(R.id.sp_ruido)
        selec_Ruido=findViewById(R.id.selec_ruido)


        //Trabajo
        val listaTrabajos= arrayOf("Seleccione", "Estable", "Sin trabajo fijo", "Desempleado")

        var adaptador:ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTrabajos)
        sp_Trabajo?.adapter=adaptador

        sp_Trabajo?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selec_Trabajo?.text=sp_Trabajo?.getSelectedItem().toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        //Estudio
        val listaEstudio= arrayOf("Seleccione", "Estudiante", "Egresado", "No estudiando")

        var adaptador2:ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaEstudio)
        sp_Estudio?.adapter=adaptador2

        sp_Estudio?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selec_Estudio?.text=sp_Estudio?.getSelectedItem().toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //Mascotas
        val listaMascota= arrayOf("Seleccione", "Perro", "Gato", "Ave", "Pez", "Roedor")

        var adaptador3:ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaMascota)
        sp_Mascota?.adapter=adaptador3

        sp_Mascota?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               selec_Mascota?.text=sp_Mascota?.getSelectedItem().toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //Relacion
        val listaRelacion= arrayOf("Seleccione", "Soltero", "En una relación")

        var adaptador4:ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaRelacion)
        sp_Relacion?.adapter=adaptador4

        sp_Relacion?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                select_Relacion?.text=sp_Relacion?.getSelectedItem().toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //Bebidas
        val listaBebidas= arrayOf("Seleccione", "Siempre", "Ocasionalmente", "Nunca")

        var adaptador5: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaBebidas)
        sp_Beber?.adapter=adaptador5

        sp_Beber?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selec_Beber?.text=sp_Beber?.getSelectedItem().toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //Fumar
        val listaFumar= arrayOf("Seleccione", "Siempre", "Ocasionalmente", "Nunca")

        var adaptador6: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaFumar)
        sp_Fumar?.adapter=adaptador6

        sp_Fumar?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selec_Fumar?.text=sp_Beber?.getSelectedItem().toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //Ruido
        val listaRuido= arrayOf("Seleccione", "No le molesta el ruido", "Tolera el ruido", "No le gusta el ruido")

        var adaptador7: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaRuido)
        sp_Ruido?.adapter=adaptador7

        sp_Ruido?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selec_Ruido?.text=sp_Ruido?.getSelectedItem().toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        //Para ir de la segunda pantalla del perfil -> seleccionar casillas de privacidad
        val btn_privacidad: Button = findViewById(R.id.btn_priv)
        btn_privacidad.setOnClickListener {

            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //Para ir de la segunda pantalla del perfil -> seleccionar casilla "guardar", te regresa a la primera pantalla del perfil
        val btn_guardar: Button = findViewById(R.id.btn_guardarEditarpp)
        btn_guardar.setOnClickListener {

            val intent: Intent = Intent(this, primera_perfil::class.java)
            startActivity(intent)
        }

        //Para que cuando el usuario haga clic en el icono de foco, lo lleve a la pantalla de consejos
        val btn_icon_consejo2: ImageView = findViewById(R.id.icono_consejos3)
        btn_icon_consejo2.setOnClickListener {

            val intent: Intent = Intent(this, consejos_pp::class.java)
            startActivity(intent)
        }

        //Para que cuando el usuario haga clic en el icono "perfil", estando en la pantalla 2 (Donde esta el boton "privacidad" y "guardar")
        //si quiere regresar a la pantalla principal de "perfil", lo regrese solo con hacer clic sobre el icono
        val btn_icon_PP1: ImageView = findViewById(R.id.icono_perfil2)
        btn_icon_PP1.setOnClickListener {

            val intent: Intent = Intent(this, primera_perfil::class.java)
            startActivity(intent)
        }
    }
}