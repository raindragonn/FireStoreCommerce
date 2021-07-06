package com.raindragonn.firestorecommerce.data.repository

import com.raindragonn.firestorecommerce.data.api.AppIntroduceApi
import com.raindragonn.firestorecommerce.data.model.Introduce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppIntroduceRepositoryImpl(
    private val introduceApi: AppIntroduceApi
) : AppIntroduceRepository {
    override suspend fun getIntroduce(): Introduce? = withContext(Dispatchers.IO) {
        introduceApi.getIntroduce()
    }
}