package com.raindragonn.firestorecommerce.data.repository

import com.raindragonn.firestorecommerce.data.model.User

interface UserRepository {

    suspend fun getUser(): User?
    suspend fun saveUser(user: User)
}