package com.raindragonn.firestorecommerce.data.api

import com.google.firebase.firestore.FirebaseFirestore
import com.raindragonn.firestorecommerce.data.model.Introduce
import kotlinx.coroutines.tasks.await

class AppIntroduceFireStoreApi(
    private val fireStore: FirebaseFirestore
) : AppIntroduceApi {
    override suspend fun getIntroduce(): Introduce? {
        return fireStore.collection("Introduce")
            .document("text")
            .get()
            .await()
            .toObject(Introduce::class.java)
    }
}