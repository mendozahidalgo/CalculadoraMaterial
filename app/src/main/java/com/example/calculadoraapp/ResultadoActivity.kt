package com.example.calculadoraapp

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calculadoraapp.models.Configuracion
import com.example.calculadoraapp.models.Resultado
import com.google.gson.Gson

class ResultadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resultado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imgBack = findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener {
            finish()
        }
        llenarResultado()
    }

    fun llenarResultado(){
        val tipoConstruccion = intent.getStringExtra("tipoConstruccion")
        val tvSubTitle = findViewById<TextView>(R.id.tvSubTitle)
        tvSubTitle.text = tipoConstruccion

        val lstGson = intent.getStringExtra("lstResultado")
        val listResultado = Gson().fromJson(lstGson,Array<Resultado>::class.java).asList()

        listResultado.forEach { item ->

            Log.i("RESULTADO",item.material)
            Log.i("RESULTADO",item.cantidadXUnidad)
            Log.i("RESULTADO",item.totalUtilizar)

        }

        val adapter = ResultadoAdapter(listResultado)
        val rvResultado = findViewById<RecyclerView>(R.id.rvResultado)

        rvResultado.layoutManager = LinearLayoutManager(this)
        rvResultado.setHasFixedSize(true)
        rvResultado.adapter = adapter
    }
}