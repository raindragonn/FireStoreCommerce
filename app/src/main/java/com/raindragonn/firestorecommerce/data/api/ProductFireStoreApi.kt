package com.raindragonn.firestorecommerce.data.api

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.raindragonn.firestorecommerce.data.model.Product
import kotlinx.coroutines.tasks.await

class ProductFireStoreApi(
    private val fireStore: FirebaseFirestore
) : ProductApi {
    override suspend fun getAllProductList(): List<Product> {
        return try {
            return fireStore.collection("Products")
                .get()
                .await()
                .map { it.toObject() }
        } catch (e: Exception) {
            emptyList<Product>()
        }
    }
}