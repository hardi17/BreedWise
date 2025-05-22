package com.hardi.breedwise.ui.subbreeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardi.breedwise.data.repository.SubBreedRepository
import com.hardi.breedwise.utils.DispatcherProvider
import com.hardi.breedwise.utils.InternetCheck.NetworkHelper
import com.hardi.breedwise.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubBreedViewModel @Inject constructor(
    private val repository: SubBreedRepository,
    private val dispatcher: DispatcherProvider,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<String>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<String>>> = _uiState

    private val _breedName = MutableStateFlow("")
    val breedName: StateFlow<String> = _breedName

    fun setBreedName(name: String) {
        if (_breedName.value != name) {
            _breedName.value = name
            loadSubBreed(name)
        }
    }

    fun loadSubBreed(breedName: String) {
        if (networkHelper.isInternetConnected()) {
            viewModelScope.launch(dispatcher.main) {
                repository.getSubBreed(breedName)
                    .flowOn(dispatcher.io)
                    .catch { e ->
                        _uiState.value = UIState.Error(e.toString())
                    }.collect {
                        _uiState.value = UIState.Success(it)
                    }
            }
        } else {
            _uiState.value = UIState.Error("something went wrong! Please check network")
        }
    }
}