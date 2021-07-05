package com.raindragonn.firestorecommerce.data.sample

import com.raindragonn.firestorecommerce.BuildConfig
import com.raindragonn.firestorecommerce.data.sample.response.SampleResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SampleDataApi {

    @GET(
        "v1/search/book.json?" +
                "display=100"
    )
    @Headers(
        value = [
            "X-Naver-Client-Id: ${BuildConfig.NAVER_CLIENT_ID}",
            "X-Naver-Client-Secret: ${BuildConfig.NAVER_CLIENT_SECRET}"
        ]
    )
    suspend fun getBooksData(
        @Query("query")
        query: String
    ): SampleResponse
}