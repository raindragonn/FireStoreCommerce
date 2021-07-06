package com.raindragonn.firestorecommerce.data.repository

import com.raindragonn.firestorecommerce.data.model.Introduce

interface AppIntroduceRepository {
    suspend fun getIntroduce(): Introduce?
}