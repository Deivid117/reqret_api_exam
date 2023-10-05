package com.dwh.gamesapp.data.api

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val urlWithParams = chain.request()
            .url
            .newBuilder()
            .build()

        val request = chain.request().newBuilder()
            .url(urlWithParams)
            .addHeader("Content-Type", "application/json")
            .build()
        return  chain.proceed(request)
    }
}