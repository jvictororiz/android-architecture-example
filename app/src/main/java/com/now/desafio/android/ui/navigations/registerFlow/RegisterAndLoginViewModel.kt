package com.now.desafio.android.ui.navigations.registerFlow

import androidx.lifecycle.MutableLiveData
import com.now.desafio.android.data.entities.Result
import com.now.desafio.android.data.entities.User
import com.now.desafio.android.service.UserRepository
import com.now.desafio.android.ui.base.BaseViewModel
import com.now.desafio.android.ui.navigations.SingleLiveEvent
import com.now.desafio.android.usecase.UserRegisterUseCase
import com.now.desafio.android.util.ext.dateToString
import com.now.desafio.android.util.ext.toDate

class RegisterAndLoginViewModel(
    val userRegisterUseCse: UserRegisterUseCase,
    val userRepository: UserRepository
) : BaseViewModel() {
    val currentUserObserver = SingleLiveEvent<User?>()
    val loadObserver = SingleLiveEvent<Boolean>()
    val errorObserver = SingleLiveEvent<String>()
    val toNextSaveObserver = SingleLiveEvent<Any>()
    val toNextUpdateObserver = SingleLiveEvent<Any>()
    val successObserver = SingleLiveEvent<User>()

    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var dateBirthday = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    fun doLogin(login: String, password: String) = launch {
        loadObserver.value = true
        val result = userRegisterUseCse.doLogin(login, password)
        if (result.isSuccessful()) {
            successObserver.value = result.data
        } else {
            errorObserver.value = result.errorMessage()
        }
        loadObserver.value = false
    }

    fun next(step: StepLogin, data: String) = launch {
        loadObserver.value = true
        val resultValidation = validateByStep(data, step)
        if (resultValidation.isSuccessful()) {
            if(step== StepLogin.CONFIRM_PASSWORD &&currentUserObserver.value != null ){
                toNextUpdateObserver.value = currentUserObserver.value
            }else{
                toNextSaveObserver.value = currentUserObserver.value
            }
        } else {
            errorObserver.value = resultValidation.errorMessage()
        }

        loadObserver.value = false
    }

    private fun validateByStep(data: String, step: StepLogin): Result<String> {
        return when (step) {
            StepLogin.NAME -> {
                val result = userRegisterUseCse.validateName(data)
                name.value = data
                result
            }
            StepLogin.EMAIL -> {
                val result = userRegisterUseCse.validateEmail(data)
                email.value = data
                result
            }
            StepLogin.BIRTHDAY -> {
                val result = userRegisterUseCse.validateBirthday(data)
                dateBirthday.value = data
                result
            }
            StepLogin.PASSWORD -> {
                val result = userRegisterUseCse.validatePassword(data)
                password.value = data
                result
            }
            StepLogin.CONFIRM_PASSWORD -> {
                val result = userRegisterUseCse.confirmPassword(password.value!!, data)
                result
            }
        }
    }

    fun saveUser() = launch {
        generateUserByFields()?.let { user ->
            val result = userRepository.saveUser(user)
            if (result.isSuccessful()) {
                successObserver.value = user
            } else {
                errorObserver.value = result.errorMessage()
            }

        }
    }

    fun updateUser() = launch {
        generateUserByFields()?.let { user ->
            val result = userRepository.updateUser(user)
            if (result.isSuccessful()) {
                successObserver.value = user
            } else {
                errorObserver.value = result.errorMessage()
            }
        }
    }

    private fun generateUserByFields(): User? {
        name.value?.let { name ->
            email.value?.let { email ->
                dateBirthday.value?.let { date ->
                    password.value?.let { password ->
                        return User(name, email, date.toDate(), password)
                    }
                }
            }
        }
        return null
    }


    fun clearUser() {
        currentUserObserver.value = null
        name.value = ""
        email.value = ""
        dateBirthday.value = ""
        password.value = ""
    }

    fun userSelected(user: User) {
        with(user) {
            currentUserObserver.value = this
            this@RegisterAndLoginViewModel.name.value = name
            this@RegisterAndLoginViewModel.email.value = email
            this@RegisterAndLoginViewModel.dateBirthday.value = dateNascimento?.dateToString()
            this@RegisterAndLoginViewModel.password.value = password

        }
    }

    enum class StepLogin {
        NAME, EMAIL, BIRTHDAY, PASSWORD, CONFIRM_PASSWORD
    }
}