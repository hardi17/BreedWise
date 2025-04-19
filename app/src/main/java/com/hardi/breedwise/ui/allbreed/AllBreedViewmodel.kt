package com.hardi.breedwise.ui.allbreed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardi.breedwise.data.repository.AllBreedRepository
import com.hardi.breedwise.utils.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class AllBreedViewmodel(
    private val repository: AllBreedRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {


    private val _breeds = MutableStateFlow<List<String>>(emptyList())
    val breeds: StateFlow<List<String>> = _breeds.asStateFlow()
    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error.asStateFlow()


    fun loadAllBreed() {
        viewModelScope.launch(dispatcher.main) {
            repository.getAllBreed()
                .flowOn(dispatcher.io)
                .catch {e ->
                    _error.value = "Error"
                }.collect {
                    _breeds.value = it
                }
        }
    }


}