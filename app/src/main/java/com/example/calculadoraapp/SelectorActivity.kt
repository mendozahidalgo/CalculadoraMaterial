package com.example.calculadoraapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calculadoraapp.models.Configuracion
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SelectorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_selector)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listConfiguracion = Gson().fromJson(materiales,Array<Configuracion>::class.java).asList()

        Log.i("SELECTOR",listConfiguracion.toString())

        val rvOpciones = findViewById<RecyclerView>(R.id.rvOpciones)
        val adapter = ConfiguracionAdapter(listConfiguracion, object : ConfiguracionAdapter.OnItemSelectedListener{
            override fun OnItemSelected(item: Configuracion) {
                Toast.makeText(this@SelectorActivity, item.construccion, Toast.LENGTH_SHORT).show()

                val intent = Intent()
                intent.putExtra("configuracion",Gson().toJson(item))
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
        })

        rvOpciones.layoutManager = LinearLayoutManager(this)
        rvOpciones.setHasFixedSize(true)
        rvOpciones.adapter = adapter

    }

    val materiales = """[
            {
                "id": "1",
                "construccion": "pared",
                "area": "mt2",
                "materiales": [
                    {
                        "material": "arena",
                        "cantidad": 40,
                        "unidadMedida": "lbs"
                    }, 
                    {
                        "material": "cemento",
                        "cantidad": "25",
                        "unidadMedida": "lbs"
                    }, 
                    {
                        "material": "agua",
                        "cantidad": 10,
                        "unidadMedida": "lts"
                    }
                ]
            },
            {
                "id": "2",
                "construccion": "terraza",
                "area": "mt2",
                "materiales": [
                    {
                        "material": "arena",
                        "cantidad": 50,
                        "unidadMedida": "lbs"
                    }, 
                    {
                        "material": "cemento",
                        "cantidad": "25",
                        "unidadMedida": "lbs"
                    }, {
                        "material": "agua",
                        "cantidad": 10,
                        "unidadMedida": "lts"
                    },
                    {
                        "material": "hierro",
                        "cantidad": 20,
                        "unidadMedida": "barillas"
                    }
                ]
            },
            {
                "id": "3",
                "construccion": "terraza",
                "area": "mt2",
                "materiales": [
                    {
                        "material": "arena",
                        "cantidad": 50,
                        "unidadMedida": "lbs"
                    }, 
                    {
                        "material": "cemento",
                        "cantidad": "25",
                        "unidadMedida": "lbs"
                    }, {
                        "material": "agua",
                        "cantidad": 10,
                        "unidadMedida": "lts"
                    },
                    {
                        "material": "hierro",
                        "cantidad": 20,
                        "unidadMedida": "barillas"
                    }
                ]
            }
        ]""".trimIndent()
}