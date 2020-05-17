package com.now.desafio.android.usecase

import com.now.desafio.android.data.entities.Artist
import com.now.desafio.android.data.entities.Result
import com.now.desafio.android.data.entities.User
import com.now.desafio.android.service.UserRepository
import com.now.desafio.android.util.ext.isValidEmail
import com.now.desafio.android.util.ext.toDate
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class UserRegisterUseCaseImpl(val userRepository: UserRepository) : UserRegisterUseCase {

    override suspend fun doLogin(login: String, password: String): Result<User> {
        if (login.isEmpty() || !login.isValidEmail()) {
            return Result.error("Login inválido")
        }

        if (password.isEmpty() || password.length < 5) {
            return Result.error("Senha inválida")
        }
        return userRepository.doLogin(login, password)
    }

    override suspend fun saveFavorite(user: User, artist: Artist): Result<User> {
        artist.favorited = !artist.favorited
        val find = user.favorites?.find { it.id == artist.id }
        if (user.favorites == null) user.favorites = ArrayList()
        if (find == null) {
            user.favorites?.add(artist)
        } else {
            user.favorites?.find { it.id == artist.id }?.favorited = artist.favorited
        }
        return userRepository.updateUser(user)
    }

    override fun validateName(name: String): Result<String> {
        if (name.isEmpty() || name.length < 3) {
            return Result.error("Preencha o seu nome para seguir em frente")
        }
        return Result.success("")
    }

    override fun validateEmail(email: String): Result<String> {
        if (email.isEmpty() || !email.isValidEmail()) {
            return Result.error("Por favor, preencha com e-mail válido")
        }
        return Result.success("user")
    }

    override fun validateBirthday(birthday: String): Result<String> {
        try {
            if (birthday.length != 10 || birthday.toDate().after(Date())) {
                return Result.error("Data preenchida é inválida")
            }
            return Result.success(birthday)
        } catch (e: Exception) {
            return Result.error("Formato da data está inválido")
        }
    }

    override fun validatePassword(password: String): Result<String> {
        if (password.isEmpty() || password.length < 5) {
            return Result.error("Por favor, preencha com uma senha válida, deve conter pelo menos 5 caracteres")
        }
        return Result.success("")
    }

    override fun confirmPassword(password: String, confirmPassword: String): Result<String> {
        return if (password == confirmPassword) {
            Result.success("null", 200)
        } else {
            Result.error("A senha informada deve ser igual a senha informada anteriormente")
        }
    }


}

interface UserRegisterUseCase {
    fun validateName(name: String): Result<String>
    fun validateEmail(email: String): Result<String>
    fun validateBirthday(birthday: String): Result<String>
    fun validatePassword(password: String): Result<String>
    fun confirmPassword(password: String, confirmPassword: String): Result<String>
    suspend fun doLogin(login: String, password: String): Result<User>
    suspend fun saveFavorite(user: User, artist: Artist): Result<User>
}