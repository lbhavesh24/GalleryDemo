package com.demo.gallerydemo.module

import com.demo.gallerydemo.APIConstants
import com.demo.gallerydemo.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideApiInterface(): ApiService {
        val retrofit = getRetrofit(okHttpClient)
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient?): Retrofit {
        return Retrofit.Builder()
            .baseUrl(APIConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient!!)
            .build()
    }

    @JvmStatic
    @get:Singleton
    @get:Provides
    val okHttpClient: OkHttpClient
        get() {
            val okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder.connectTimeout(APIConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            okHttpClientBuilder.readTimeout(APIConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS)
            okHttpClientBuilder.writeTimeout(APIConstants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY))
            return okHttpClientBuilder.build()
        }
}