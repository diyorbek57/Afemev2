package com.ayizor.afeme.api.main

import android.content.Context
import com.ayizor.afeme.manager.PrefsManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class Client {
    companion object {
        private var retrofit: Retrofit? = null

        fun getClient(context: Context): Retrofit? {
            if (retrofit == null) {
                var log = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder()
                    .connectTimeout(40, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS).addInterceptor(
                        HeaderInterceptor(context)
                    ).addInterceptor(log).build()
                retrofit = Retrofit.Builder()
                    .baseUrl(Api.BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }


    }

}