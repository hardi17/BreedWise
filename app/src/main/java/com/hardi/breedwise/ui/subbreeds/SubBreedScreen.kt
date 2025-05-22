package com.hardi.breedwise.ui.subbreeds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hardi.breedwise.ui.base.ShowError
import com.hardi.breedwise.ui.base.ShowLoading
import com.hardi.breedwise.ui.base.TopAppBarWithOutIconUI
import com.hardi.breedwise.utils.UIState

@Composable
fun SubBreedScreen(
    breedName: String,
    onSubBreedClick: (breedName: String, name: String) -> Unit,
    viewModel: SubBreedViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(breedName) {
        viewModel.setBreedName(breedName)
    }

    Scaffold(
        topBar = {
            TopAppBarWithOutIconUI(
                title = breedName.uppercase()
            )
        }, content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                SubBreedContent(
                    uiState,
                    onSubBreedClick,
                    breedName
                )
            }

        }
    )
}

@Composable
fun SubBreedContent(
    uiState: UIState<List<String>>,
    onSubBreedClick: (breedName: String, name: String) -> Unit,
    breedName: String
) {
    when (uiState) {
        is UIState.Success -> {
            SubBreedList(uiState.data, onSubBreedClick, breedName)
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
fun SubBreedList(data: List<String>, onSubBreedClick: (breedName: String, name: String) -> Unit, breedName: String) {
    LazyColumn {
        items(data, key = { name -> name }) { item ->
            SubBreedItem(item, onSubBreedClick, breedName)
        }
    }
}

@Composable
fun SubBreedItem(item: String, onSubBreedClick: (breedName: String, name: String) -> Unit, breedName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSubBreedClick(breedName, item)
            }
    ) {
        Text(
            text = item.uppercase(),
            color = Color.Black,
            modifier = Modifier.padding(12.dp)
        )
    }

}
