package com.example.myborutocomposepaging3app.domain.usecases

import com.example.myborutocomposepaging3app.data.repository.Repository

class SaveOnBoardingUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(completed: Boolean) {
        repository.saveOnBoardingState(completed = completed)
    }
}