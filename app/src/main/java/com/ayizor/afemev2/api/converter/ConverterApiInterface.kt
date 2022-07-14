package com.ayizor.afeme.api.converter

import com.ayizor.afeme.model.convertermodels.Currency
import retrofit2.Call
import retrofit2.http.GET
import java.util.*

interface ConverterApiInterface {

    //Post Interface
    @GET("json/")
    fun getCurrency(): Call<Currency>
}