package com.hardi.breedwise.ui.allbreed

import app.cash.turbine.test
import com.hardi.breedwise.data.model.DogBreeds
import com.hardi.breedwise.data.repository.AllBreedRepository
import com.hardi.breedwise.utils.DispatcherProvider
import com.hardi.breedwise.utils.InternetCheck.NetworkHelper
import com.hardi.breedwise.utils.TestDispatcherProvider
import com.hardi.breedwise.utils.UIState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AllBreedViewmodelTest {

    @Mock
    private lateinit var repository: AllBreedRepository

    @Mock
    private lateinit var networkHelper: NetworkHelper

    private lateinit var dispatcher: DispatcherProvider

    @Before
    fun setUp() {
        dispatcher = TestDispatcherProvider()
    }

    @Test
    fun get_all_breed_succeefully_test() {
        runTest {
            val dogBreeds = DogBreeds("breed", listOf("subBreed1", "subBreed2"))
            val result = listOf(dogBreeds)
            doReturn(true)
                .`when`(networkHelper)
                .isInternetConnected()
            doReturn(flowOf(result))
                .`when`(repository)
                .getAllBreed()

            val viewModel = AllBreedViewmodel(repository, dispatcher, networkHelper)

            viewModel.loadAllBreed()

            viewModel.uiState.test {
                assertEquals(UIState.Success(result), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(repository, times(1)).getAllBreed()
        }
    }

    @Test
    fun get_all_breed_failed_test() {
        runTest {
            val error = IllegalAccessException("Something went wrong")
            doReturn(true)
                .`when`(networkHelper)
                .isInternetConnected()
            doReturn(flow<List<DogBreeds>> {
                throw error
            }).`when`(repository).getAllBreed()

            val viewModel = AllBreedViewmodel(repository, dispatcher, networkHelper)

            viewModel.loadAllBreed()

            viewModel.uiState.test {
                assertEquals(UIState.Error(error.toString()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }


            verify(repository, times(1)).getAllBreed()
        }

    }


}