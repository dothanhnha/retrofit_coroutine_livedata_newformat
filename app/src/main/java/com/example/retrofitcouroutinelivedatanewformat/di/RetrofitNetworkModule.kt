package com.example.retrofitcouroutinelivedatanewformat

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitNetworkModule {
    private val BASE_URL = "https://api.github.com"

    private val CONNECT_TIMEOUT_SECOND: Long = 20
    private val WRITE_TIMEOUT_SECOND: Long = 20
    private val READ_TIMEOUT_SECOND: Long = 20
    private val convertDateTime: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .create()

    @Provides
    @Singleton
    fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECOND * 1000, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECOND * 1000, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT_SECOND * 1000, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun getRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(convertDateTime))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun currentWeatherAPI(retrofit: Retrofit): SearchRepositoriesApi {
        return retrofit.create(SearchRepositoriesApi::class.java)
    }

}