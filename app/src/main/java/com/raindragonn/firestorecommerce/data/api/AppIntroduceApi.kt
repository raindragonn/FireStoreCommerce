package com.raindragonn.firestorecommerce.data.api

import com.raindragonn.firestorecommerce.data.model.Introduce

interface AppIntroduceApi {
    suspend fun getIntroduce(): Introduce?
}