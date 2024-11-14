package com.example.calculadoraapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculadoraapp.models.Configuracion
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var tvElegir: EditText
    private var confMaterial: Configuracion? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        var tvHola = this.findViewById<TextView>(R.id.tvHola)
//        tvHola.text = "Grupo # 5"

        tvElegir = findViewById(R.id.etElegir)

        tvElegir.setOnTouchListener(View.OnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP){
                if (event.rawX >= tvElegir.right - tvElegir.compoundDrawables[2].bounds.width()){
                    val intent = Intent(this,SelectorActivity::class.java)
                    startForResultSelectorConf.launch(intent)
                    return@OnTouchListener true
                }
            }
            false
        })

        val btnElegir = findViewById<Button>(R.id.btnElegir)

        btnElegir.setOnClickListener {
            val intent = Intent(this,SelectorActivity::class.java)
            startForResultSelectorConf.launch(intent)
        }

        val tvCantidad = findViewById<EditText>(R.id.etCantidad)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        btnCalcular.setOnClickListener{
            var msg = ""
            if (confMaterial == null){
                msg = "Elegir tipo de construccion"
            } else {
                if (tvCantidad.text.isNullOrEmpty()){
                    msg = "Ingresar una cantidad"
                }
            }
            if (msg.isEmpty()){
                val cant = tvCantidad.text.toString().toInt()
                Log.i("Tipo Construccion", confMaterial!!.construccion)
                confMaterial!!.materiales.forEach{item ->
                    val total = item.cantidad * cant
                    Log.i("MATERIALES",item.material)
                    Log.i("MATERIALES","Cantidad: ${item.cantidad} ${item.unidadMedida} por ${confMaterial!!.area} total: ${total}")
                }
            } else {
                val snack = Snackbar.make(findViewById(R.id.main),msg,Snackbar.LENGTH_SHORT)
                snack.show()
            }

        }

    }


    private val startForResultSelectorConf = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result: ActivityResult ->

        if (result.resultCode == Activity.RESULT_OK){
            val intent = result.data
            if (intent != null){
                val confJson = intent.getStringExtra("configuracion")
                confMaterial = Gson().fromJson(confJson, Configuracion::class.java)
                if (confMaterial != null){
                    tvElegir.setText(confMaterial!!.construccion)
                }
            }
        }

    }
}