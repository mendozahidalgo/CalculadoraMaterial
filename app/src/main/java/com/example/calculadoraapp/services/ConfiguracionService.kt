package com.example.calculadoraapp.services

import com.example.calculadoraapp.models.Configuracion
import retrofit2.Call
import retrofit2.http.GET

interface ConfiguracionService {

    @GET("conf-materiales")
    fun getConfiguracionMateriales(): Call<List<Configuracion>>
}