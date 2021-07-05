package com.raindragonn.firestorecommerce.util

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.raindragonn.firestorecommerce.data.model.Product
import com.raindragonn.firestorecommerce.data.model.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object DataGenerator {
    fun generate(list: List<Product>) {
        val fireStore = Firebase.firestore.collection("Products")

        list.forEach {
            fireStore.add(it)
        }
    }

    suspend fun reViewGenerate(userId: String, productId: String) = withContext(Dispatchers.IO) {
        val fireStore = Firebase.firestore
        val reviewRef = Firebase.firestore.collection("Reviews")
        val productReference = fireStore.collection("Products").document(productId)

        (0..30).forEach {
            val review = Review(
                userId = userId,
                productId = productId,
                content = "너무 재밌어요 $it",
                star = (0..5).toMutableList().apply { shuffle() }[0].toFloat()
            )

            reviewRef.add(review)

            fireStore.runTransaction { transaction ->
                val product = transaction.get(productReference).toObject<Product>()!!
                val oldTotalReviewer = product.totalReviewer ?: 0
                val oldTotalStar = product.totalStar ?: 0f
                val newTotalReviewer = oldTotalReviewer + 1
                val newTotalStar =
                    oldTotalStar + ((0..5).toMutableList().apply { shuffle() }[0].toFloat())

                transaction.set(
                    productReference,
                    product.copy(
                        totalReviewer = newTotalReviewer,
                        totalStar = newTotalStar
                    )
                )
            }.await()
        }
    }
}