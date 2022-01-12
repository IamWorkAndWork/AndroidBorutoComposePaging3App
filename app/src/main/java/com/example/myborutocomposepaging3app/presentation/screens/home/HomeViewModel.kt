package com.example.myborutocomposepaging3app.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.example.myborutocomposepaging3app.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    val getAllHeroes = useCases.getAllHeroesUseCase()

}