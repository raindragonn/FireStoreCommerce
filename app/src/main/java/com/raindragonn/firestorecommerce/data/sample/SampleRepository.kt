package com.raindragonn.firestorecommerce.data.sample

import com.raindragonn.firestorecommerce.BuildConfig
import com.raindragonn.firestorecommerce.data.sample.response.SampleItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object SampleRepository {

    suspend fun getSampleData(query: String): List<SampleItem> = withContext(Dispatchers.IO) {
        sampleService.getBooksData(query).items ?: emptyList()
    }

    private val sampleService: SampleDataApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
            .create()
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                } else {
                    level = HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()
    }
}