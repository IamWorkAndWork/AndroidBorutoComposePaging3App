package com.example.myborutocomposepaging3app.domain.usecases

import com.example.myborutocomposepaging3app.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadOnBoardingUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readOnBoardingState()
    }
}