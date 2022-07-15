package com.ayizor.afeme.api.converter

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConverterClient {
    companion object {
        private var retrofit: Retrofit? = null
        fun getClient(): Retrofit? {
            if (retrofit == null) {
                val client = OkHttpClient.Builder()
                    .addInterceptor(ConverterHeaderInterceptor()).build()
                retrofit = Retrofit.Builder()
                    .baseUrl(ConverterApi.BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }

}