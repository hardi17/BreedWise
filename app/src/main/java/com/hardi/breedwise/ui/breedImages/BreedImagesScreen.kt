package com.hardi.breedwise.ui.breedImages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.hardi.breedwise.R
import com.hardi.breedwise.ui.base.ShowError
import com.hardi.breedwise.ui.base.ShowLoading
import com.hardi.breedwise.ui.base.TopAppBarWithOutIconUI
import com.hardi.breedwise.utils.UIState

@Composable
fun BreedImagesScreen(
    breedName: String,
    subBreedName: String,
    viewModel: BreedImagesViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(breedName, subBreedName) {
        viewModel.setSubBreedName(breedName, subBreedName)
    }

    Scaffold(
        topBar = {
            TopAppBarWithOutIconUI(
                title = subBreedName.uppercase()
            )
        }, content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                BreedImageContent(
                    uiState
                )
            }
        })
}

@Composable
fun BreedImageContent(uiState: UIState<List<String>>) {
    when (uiState) {
        is UIState.Success -> {
            BreedImageList(uiState.data)
        }

        is UIState.Loading -> {
            ShowLoading()
        }

        is UIState.Error -> {
            uiState.message?.let { ShowError(it) }
        }
    }
}

@Composable
fun BreedImageList(imgUrls: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 columns
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(imgUrls) { url ->
            BreedImages(url)
        }
    }
}

@Composable
fun BreedImages(item: String) {
    AsyncImage(
        model = item,
        contentDescription = stringResource(R.string.sub_breed_img),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    )
}
