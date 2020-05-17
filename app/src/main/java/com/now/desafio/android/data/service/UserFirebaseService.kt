package com.now.desafio.android.data.service

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.now.desafio.android.data.entities.Artist
import com.now.desafio.android.data.entities.Result
import com.now.desafio.android.data.entities.User
import com.now.desafio.android.service.UserRepositoryImpl
import com.now.desafio.android.util.ext.backgroundCall
import com.now.desafio.android.util.ext.backgroundSave


class UserFirebaseServiceImpl(private val firebaseDatabase: FirebaseDatabase) : UserFirebaseService {

    override suspend fun doLoginUser(login: String, password: String): Result<User> {
        val result = firebaseDatabase.getReference(UserRepositoryImpl.TABLE_USERS).backgroundCall<User>(chield = login)
        if (result.isSuccessful()) {
            result.data?.let {
                if (it.password == password) return Result.success(it)
            }
            return Result.error(throwable = Throwable("Login ou senha incorretos"))
        }
        return Result.error(result.errorMessage())
    }

    override suspend fun saveUser(user: User): Result<User> {
        user.email?.let {
            val result = firebaseDatabase.getReference(UserRepositoryImpl.TABLE_USERS).backgroundCall<User>(chield = user.email)
            if (result.data != null) return Result.error(Throwable("E-mail já cadastrado, por favor escolha outro"))
            val resultSave = firebaseDatabase.getReference(UserRepositoryImpl.TABLE_USERS).backgroundSave(it, user)
            if (resultSave.isSuccessful()) {
                return Result.success(user)
            }
            return Result.error(result.errorMessage())
        }
        throw Throwable("Email está null")
    }

    override suspend fun updateUser(user: User): Result<User> {
        user.email?.let {
            val resultSave = firebaseDatabase.getReference(UserRepositoryImpl.TABLE_USERS).backgroundSave(it, user)
            return if (resultSave.isSuccessful()) {
                Result.success(user)
            }else{
                Result.error(resultSave.errorMessage())
            }
        }
        throw Throwable("Email está null")
    }
}

interface UserFirebaseService {
    suspend fun doLoginUser(login: String, password: String): Result<User>
    suspend fun saveUser(user: User): Result<User>
    suspend fun updateUser(user: User): Result<User>
}
