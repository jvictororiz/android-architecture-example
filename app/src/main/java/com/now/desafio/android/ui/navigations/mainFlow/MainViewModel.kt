package com.now.desafio.android.ui.navigations.mainFlow

import androidx.lifecycle.MutableLiveData
import com.now.desafio.android.R
import com.now.desafio.android.data.entities.Artist
import com.now.desafio.android.data.entities.User
import com.now.desafio.android.ui.base.BaseViewModel
import com.now.desafio.android.usecase.FindArtistUseCase
import com.now.desafio.android.usecase.UserRegisterUseCase

class MainViewModel(
    private val findUserUseCase: FindArtistUseCase,
    private val userUseCase: UserRegisterUseCase
) : BaseViewModel() {
    val userObserver = MutableLiveData<User>()
    val loadUsesrObserver = MutableLiveData<Boolean>()
    val resultUsersObserver = MutableLiveData<List<Artist>>()
    val resultUsersWithFilterObserver = MutableLiveData<List<Artist>>()
    val alertOfflineObserver = MutableLiveData<Int>()
    val errorObserver = MutableLiveData<String>()


    fun listAllArtists() = launch {
        loadUsesrObserver.value = true
        userObserver.value?.let {
            val result = findUserUseCase.listAllArtists(it)
            if (result.isSuccessful()) {
                resultUsersObserver.value = result.data
            } else if (result.isCacheSuccessful() && result.data?.isNullOrEmpty() == false) {
                alertOfflineObserver.value = R.string.alert_offline
                resultUsersObserver.value = result.data
            } else {
                errorObserver.value = result.errorMessage()
            }
            loadUsesrObserver.value = false
        }
    }

    fun applyFilter(text: String) {
        resultUsersWithFilterObserver.value = resultUsersObserver.value?.filter {
            (it.name.contains(text) || text.contains(it.name) || (it.username.contains(text) || text.contains(it.username)))
        }
    }

    fun changeFavorite(artist: Artist) = launch {
        userObserver.value?.let {
            userUseCase.saveFavorite(it, artist)
            listAllArtists()
        }
    }

    fun setUser(it: User) {
        userObserver.value = it
    }
}