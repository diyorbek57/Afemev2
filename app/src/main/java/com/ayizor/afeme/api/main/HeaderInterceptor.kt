package com.ayizor.afeme.api.main

import android.content.Context
import com.ayizor.afeme.manager.PrefsManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        request = request.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "Bearer ${PrefsManager(context).loadUserRegisteredToken()}")
            .build()
        return chain.proceed(request)
    }
}