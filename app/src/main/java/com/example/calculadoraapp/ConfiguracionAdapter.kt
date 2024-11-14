package com.example.calculadoraapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calculadoraapp.models.Configuracion

class ConfiguracionAdapter(val items: List<Configuracion>, val listener: ConfiguracionAdapter.OnItemSelectedListener): RecyclerView.Adapter<ConfiguracionAdapter.ConfViewHolder>() {


    inner class ConfViewHolder(val view: View): RecyclerView.ViewHolder(view){

        fun bind(item: Configuracion){
            val tvconfig = view.findViewById<TextView>(R.id.tvConfig)
            val tvArea = view.findViewById<TextView>(R.id.tvArea)
            tvconfig.text = "Tipo construccion: ${item.construccion}"
            tvArea.text = "Area: ${item.area}"

            view.setOnClickListener{
                listener.OnItemSelected(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ConfViewHolder(inflater.inflate(R.layout.configuracion_item,parent,false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ConfViewHolder, position: Int) {
        holder.bind(items[position])
    }

    interface OnItemSelectedListener{
        fun OnItemSelected(item: Configuracion)
    }
}