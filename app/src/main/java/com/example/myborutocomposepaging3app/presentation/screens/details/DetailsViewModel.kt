package com.example.myborutocomposepaging3app.presentation.screens.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity
import com.example.myborutocomposepaging3app.domain.usecases.UseCases
import com.example.myborutocomposepaging3app.util.Constant.DETAILS_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedHero = MutableStateFlow<HeroEntity?>(null)
    val selectedHero: StateFlow<HeroEntity?> = _selectedHero

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val heroId = savedStateHandle.get<Int>(DETAILS_ARGUMENT_KEY)
            heroId?.let {
                _selectedHero.value = useCases.getSelectedHeroUseCase(heroId = heroId)
            }
        }
    }

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val _colorPalatte = mutableStateOf<Map<String, String>>(mapOf())
    val colorPalatte: State<Map<String, String>> = _colorPalatte

    fun generateColorPalatte() {
        viewModelScope.launch {
            _uiEvent.emit(
                UiEvent.GenerateColorPalatte
            )
        }
    }

    fun setColorPalatte(colors: Map<String, String>) {
        _colorPalatte.value = colors
    }

}

sealed class UiEvent {
    object GenerateColorPalatte : UiEvent()
}