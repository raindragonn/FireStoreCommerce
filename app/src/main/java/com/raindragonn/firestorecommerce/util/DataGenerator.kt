package com.raindragonn.firestorecommerce.util

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.raindragonn.firestorecommerce.data.Product

object DataGenerator {
    fun generate(list: List<Product>) {
        val fireStore = Firebase.firestore.collection("Products")

        list.forEach {
            fireStore.add(it)
        }
    }
}