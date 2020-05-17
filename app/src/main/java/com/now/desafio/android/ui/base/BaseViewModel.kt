package com.now.desafio.android.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean


abstract class BaseViewModel : ViewModel() {

    fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }

}
