package com.example.calculadoraapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculadoraapp.models.Configuracion
import com.example.calculadoraapp.models.Resultado
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

        val imgInfo = findViewById<ImageView>(R.id.imgInfo)
        imgInfo.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }

        tvElegir = findViewById(R.id.etElegir)
        val imgSearch = findViewById<ImageView>(R.id.imgSearch)

        imgSearch.setOnClickListener {
            val intent = Intent(this,SelectorActivity::class.java)
            startForResultSelectorConf.launch(intent)
        }

        val tvCantidad = findViewById<EditText>(R.id.etCantidad)


        var texto = ""
        tvCantidad.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                texto = tvCantidad.text.toString()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (texto.isNotEmpty()){
                    if (texto.contains(".")){
                        var countP = 0
                        p0?.forEach { c ->
                            if (c.toString() == "."){
                                countP += 1
                            }
                        }
                        if (countP > 1){
                            tvCantidad.setText(texto)
                        }

                        if (countP == 1){
                            val lastIndex = texto.indexOf(".")
                            val decimales = texto.substring(lastIndex+1, texto.length)
                            if (decimales.length == 2){
                                val newLastIndex = p0.toString().indexOf(".")
                                val newDecimales = p0.toString().substring(newLastIndex+1, p0.toString().length)
                                if (newDecimales.length > 2){
                                    tvCantidad.setText(texto)
                                }
                            }
                        }
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        btnCalcular.setOnClickListener{
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            var msg = ""
            if (confMaterial == null){
                msg = "Elegir tipo de construccion"
            } else {
                if (tvCantidad.text.isNullOrEmpty()){
                    msg = "Ingresar una cantidad"
                }
            }
            if (msg.isEmpty()){
                val cant = tvCantidad.text.toString().toDouble()
                val lstResultado: MutableList<Resultado> = mutableListOf()
                Log.i("Tipo Construccion", confMaterial!!.construccion)
                confMaterial!!.materiales.forEach{item ->
                    val total = item.cantidad * cant
                    val cantidadXUnidad = "${item.cantidad} ${item.unidadMedida} por ${confMaterial!!.area}"
                    val totalUtilizar = "Total a utilizar: ${total} ${item.unidadMedida}"
                    lstResultado.add(Resultado(item.material,cantidadXUnidad,totalUtilizar))
                }

                if (lstResultado.isNotEmpty()){
                    val tipoConstruccion = "Material a utilizar para: \n ${cant} ${confMaterial!!.area} de ${confMaterial!!.construccion}"
                    val lstGson = Gson().toJson(lstResultado)
                    val intent = Intent(this@MainActivity,ResultadoActivity::class.java)
                    intent.putExtra("tipoConstruccion", tipoConstruccion)
                    intent.putExtra("lstResultado",lstGson)
                    startActivity(intent)
                } else {
                    val snack = Snackbar.make(findViewById(R.id.main),"Configuracion incompleta, calculo no realizado",Snackbar.LENGTH_SHORT)
                    snack.show()
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