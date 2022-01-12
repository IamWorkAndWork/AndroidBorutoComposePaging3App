package com.example.myborutocomposepaging3app.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity
import com.example.myborutocomposepaging3app.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedHeroes = MutableStateFlow<PagingData<HeroEntity>>(PagingData.empty())
    val searchedHeroes = _searchedHeroes

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchHeroes(query: String) {
        viewModelScope.launch {
            useCases.searchHeroesUseCase(query = query)
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _searchedHeroes.value = it
                }
        }
    }

}