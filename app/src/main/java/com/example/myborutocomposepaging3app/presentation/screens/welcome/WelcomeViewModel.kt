package com.example.myborutocomposepaging3app.presentation.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myborutocomposepaging3app.domain.model.OnBoardingPage
import com.example.myborutocomposepaging3app.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch {
            useCases.saveOnBoardingUseCase.invoke(completed = completed)
        }
    }

    fun getPagesList(): List<OnBoardingPage> {
        return listOf(
            OnBoardingPage.First,
            OnBoardingPage.Second,
            OnBoardingPage.Third
        )
    }

}