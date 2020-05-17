package com.now.desafio.android.service

import com.now.desafio.android.data.entities.Result
import com.now.desafio.android.data.entities.User
import com.now.desafio.android.data.service.UserFirebaseService

class UserRepositoryImpl(
    private val userFirebaseService: UserFirebaseService
) : UserRepository {
    override suspend fun doLogin(login: String, password: String): Result<User> {
        return userFirebaseService.doLoginUser(login, password)
    }

    override suspend fun saveUser(user: User): Result<User> {
        return userFirebaseService.saveUser(user)
    }

    override suspend fun updateUser(user: User): Result<User> {
        return userFirebaseService.updateUser(user)
    }

    companion object {
        const val TABLE_USERS = "USERS"
    }
}
interface UserRepository {
    suspend fun doLogin(login: String, password: String): Result<User>
    suspend fun saveUser(user: User): Result<User>
    suspend fun updateUser(user: User): Result<User>
}


