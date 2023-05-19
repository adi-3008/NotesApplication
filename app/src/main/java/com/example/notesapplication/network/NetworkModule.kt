package com.example.notesapplication.network

import com.example.notesapplication.login.api.UserAPI
import com.example.notesapplication.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofitBuilder() : Builder {
        return Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(authNetworkInterceptor: AuthNetworkInterceptor) : OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authNetworkInterceptor).build()
    }

    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Builder) : UserAPI {
        return retrofitBuilder.build().create(UserAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesAuthRetrofit(retrofitBuilder: Builder, okHttpClient: OkHttpClient) : Retrofit{
        return retrofitBuilder
            .client(okHttpClient)
            .build()
    }

}