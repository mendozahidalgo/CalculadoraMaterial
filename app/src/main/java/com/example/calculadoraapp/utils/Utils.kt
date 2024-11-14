package com.example.calculadoraapp.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Utils {

    val CONF_ENPOINT = "conf-materiales"

    companion object{
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://private-83080-configmaterial.apiary-mock.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}