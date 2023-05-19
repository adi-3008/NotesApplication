package com.example.notesapplication.network

import com.example.notesapplication.storage.SharedPreference
import com.example.notesapplication.utils.Constants.USER_TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthNetworkInterceptor @Inject constructor(): Interceptor {

    @Inject
    lateinit var sharedPreference: SharedPreference

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // builder to add token in request.
        val builder = request.newBuilder()

        // get the token stored in sharedPreference.
        val token = sharedPreference.getData(USER_TOKEN)

        builder.addHeader("Authorization", "Bearer $token")

        return chain.proceed(builder.build())
    }
}