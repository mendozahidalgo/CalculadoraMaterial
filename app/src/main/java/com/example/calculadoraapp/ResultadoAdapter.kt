package com.example.calculadoraapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calculadoraapp.models.Resultado

class ResultadoAdapter(val items: List<Resultado>): RecyclerView.Adapter<ResultadoAdapter.ResultadoViewHolder>() {

    inner class ResultadoViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun bind(item: Resultado){
            val tvMaterial = view.findViewById<TextView>(R.id.tvMaterial)
            val tvCantidaXUnidad = view.findViewById<TextView>(R.id.tvCantidaXUnidad)
            val tvTotalUtilizar = view.findViewById<TextView>(R.id.tvTotalUtilizar)

            tvMaterial.text = item.material
            tvCantidaXUnidad.text = item.cantidadXUnidad
            tvTotalUtilizar.text = item.totalUtilizar
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultadoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ResultadoViewHolder(inflater.inflate(R.layout.resultado_item,parent,false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ResultadoViewHolder, position: Int) {
        holder.bind(items[position])
    }
}