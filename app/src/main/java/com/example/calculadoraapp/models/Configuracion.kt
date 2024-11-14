package com.example.calculadoraapp.models

data class Configuracion(var id: String = "", var construccion: String = "", var area: String = "", var materiales: List<Material> = listOf() )
