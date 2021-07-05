package com.raindragonn.firestorecommerce.data.api

import com.google.firebase.firestore.FirebaseFirestore
import com.raindragonn.firestorecommerce.data.model.User
import kotlinx.coroutines.tasks.await

class UserFireStoreApi(
    private val fireStore: FirebaseFirestore
) : UserApi {

    override suspend fun saveUser(user: User): User =
        fireStore.collection("Users")
            .add(user)
            .await()
            .let { User(it.id) }
}
