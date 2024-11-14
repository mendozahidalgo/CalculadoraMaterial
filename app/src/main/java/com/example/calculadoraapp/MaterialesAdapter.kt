package com.example.calculadoraapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calculadoraapp.models.Material

class MaterialesAdapter(val items: List<Material>): RecyclerView.Adapter<MaterialesAdapter.MaterialViewHolder>() {

    inner class MaterialViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(item: Material){
            val tvMaterial = view.findViewById<TextView>(R.id.tvMaterial)
            val tvCantidadMat = view.findViewById<TextView>(R.id.tvCantidadMat)
            val tvUnidadMedida = view.findViewById<TextView>(R.id.tvUnidadMedida)

            tvMaterial.text = "Material: ${item.material}"
            tvCantidadMat.text = "Cantidad: ${item.cantidad}"
            tvUnidadMedida.text = "Unidad de medida: ${item.unidadMedida}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MaterialViewHolder(inflater.inflate(R.layout.material_item,parent,false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MaterialViewHolder, position: Int) {
        holder.bind(items[position])
    }
}