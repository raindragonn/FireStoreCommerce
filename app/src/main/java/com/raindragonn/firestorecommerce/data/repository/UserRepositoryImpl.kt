package com.raindragonn.firestorecommerce.data.repository

import com.raindragonn.firestorecommerce.data.api.UserApi
import com.raindragonn.firestorecommerce.data.model.User
import com.raindragonn.firestorecommerce.data.preference.SharedPreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val preferenceManager: SharedPreferenceManager
) : UserRepository {
    override suspend fun getUser(): User? = withContext(Dispatchers.IO) {
        preferenceManager.userId?.let { User(it) }
    }

    override suspend fun saveUser(user: User) = withContext(Dispatchers.IO) {
        val newUser = userApi.saveUser(user)
        preferenceManager.userId = newUser.id
    }
}
