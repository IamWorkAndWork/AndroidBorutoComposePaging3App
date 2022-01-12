package com.example.myborutocomposepaging3app.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myborutocomposepaging3app.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _onBoardCompleted = MutableStateFlow(false)
    val onBoardingCompleted: StateFlow<Boolean> = _onBoardCompleted

    init {
        viewModelScope.launch {
            useCases.readOnBoardingUseCase()
                .flowOn(Dispatchers.IO)
                .collect { isCompleted ->
                    _onBoardCompleted.value = isCompleted
                }
        }
    }

}