package com.raindragonn.firestorecommerce.data.api

import com.raindragonn.firestorecommerce.data.model.User

interface UserApi {
    suspend fun saveUser(user: User): User
}