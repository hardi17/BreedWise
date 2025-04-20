package com.hardi.breedwise.ui.allbreed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardi.breedwise.data.model.DogBreeds
import com.hardi.breedwise.data.repository.AllBreedRepository
import com.hardi.breedwise.utils.DispatcherProvider
import com.hardi.breedwise.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllBreedViewmodel @Inject constructor(
    private val repository: AllBreedRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {


    private val _uiState = MutableStateFlow<UIState<List<DogBreeds>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<DogBreeds>>> = _uiState


    fun loadAllBreed() {
        viewModelScope.launch(dispatcher.main) {
            repository.getAllBreed()
                .flowOn(dispatcher.io)
                .catch { e ->
                    _uiState.value = UIState.Error(e.toString())
                }.collect {
                    _uiState.value = UIState.Success(it)
                }
        }
    }


}