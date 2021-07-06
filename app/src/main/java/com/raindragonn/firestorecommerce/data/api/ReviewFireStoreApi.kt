package com.raindragonn.firestorecommerce.data.api

import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import com.raindragonn.firestorecommerce.data.model.Product
import com.raindragonn.firestorecommerce.data.model.Review
import kotlinx.coroutines.tasks.await

class ReviewFireStoreApi(
    private val fireStore: FirebaseFirestore
) : ReviewApi {
    override suspend fun getLatestProductReviewSnapshot(
        productId: String,
        lastDocumentSnapshot: DocumentSnapshot?
    ): List<Review> {
        val reviewsRef = fireStore.collection("Reviews")

        return reviewsRef
            .whereEqualTo("productId", productId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .endAt(lastDocumentSnapshot)
            .get()
            .await()
            .map { it.toObject<Review>() }
    }

    override suspend fun getLimitProductReviewSnapshot(
        productId: String,
        lastDocumentSnapshot: DocumentSnapshot?,
        limit: Long
    ): QuerySnapshot {
        val reviewsRef = fireStore.collection("Reviews")

        return lastDocumentSnapshot?.let {
            reviewsRef
                .whereEqualTo("productId", productId)
                .orderBy("createdAt", Query.Direction.DESCENDING).startAfter(it)
                .limit(limit)
                .get()
                .await()
        } ?: run {
            reviewsRef
                .whereEqualTo("productId", productId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(limit)
                .get()
                .await()
        }
    }

    override suspend fun addReview(review: Review): Review {
        val newReviewsRef = fireStore.collection("Reviews").document()
        val productsRef = fireStore.collection("Products").document(review.productId!!)

        fireStore.runTransaction { transaction ->
            val product = transaction.get(productsRef).toObject<Product>()!!

            val oldTotalReviewer = product.totalReviewer ?: 0
            val oldTotalStar = product.totalStar ?: 0f

            val newTotalReviewer = oldTotalReviewer + 1
            val newTotalStar = oldTotalStar + (review.star ?: 0f)

            transaction.set(
                productsRef,
                product.copy(
                    totalReviewer = newTotalReviewer,
                    totalStar = newTotalStar
                )
            )
            transaction.set(
                newReviewsRef,
                review,
                SetOptions.merge()
            )
        }.await()

        return newReviewsRef.get().await().toObject<Review>()!!
    }

    override suspend fun removeReview(review: Review) {
        val reviewsRef = fireStore.collection("Reviews").document(review.id!!)
        val productsRef = fireStore.collection("Products").document(review.productId!!)

        fireStore.runTransaction { transaction ->
            val product = transaction
                .get(productsRef)
                .toObject<Product>()!!

            val oldTotalReviewer = product.totalReviewer ?: 0
            val oldTotalStar = product.totalStar ?: 0f

            val newTotalReviewer = oldTotalReviewer - 1
            val newTotalStar = oldTotalStar - (review.star ?: 0f)

            transaction.set(
                productsRef,
                product.copy(
                    totalStar = newTotalStar,
                    totalReviewer = newTotalReviewer
                )
            )

            transaction.delete(reviewsRef)
        }.await()
    }
}
