package com.hardi.breedwise.ui.allbreed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hardi.breedwise.R
import com.hardi.breedwise.data.model.DogBreeds
import com.hardi.breedwise.ui.base.ShowError
import com.hardi.breedwise.ui.base.ShowLoading
import com.hardi.breedwise.ui.base.TopAppBarWithOutIconUI
import com.hardi.breedwise.utils.UIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllBreedScreen(
    onBreedClick: (name: String) -> Unit,
    viewModel: AllBreedViewmodel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBarWithOutIconUI(
                title = stringResource(id = R.string.all_breed_title)
            )
        }, content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                AllBreedContent(
                    uiState = uiState,
                    onBreedClick = onBreedClick
                )
            }

        }
    )

}

@Composable
fun AllBreedList(dogBreed: List<DogBreeds>, onBreedClick: (name: String) -> Unit) {
    LazyColumn {
        items(dogBreed, key = { breed -> breed.breed }) { breed ->
            AllBreedItem(breed, onBreedClick)
        }
    }
}

@Composable
fun AllBreedItem(breed: DogBreeds, onBreedClick: (name: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onBreedClick(breed.breed)
            }
    ) {
        Text(
            text = breed.breed.uppercase(),
            color = Color.Black,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun AllBreedContent(
    uiState: UIState<List<DogBreeds>>,
    onBreedClick: (name: String) -> Unit
) {
    when (uiState) {
        is UIState.Success -> {
            AllBreedList(uiState.data, onBreedClick)
        }

        is UIState.Loading -> {
            ShowLoading()
        }

        is UIState.Error -> {
            uiState.message?.let { ShowError(it) }
        }
    }
}
