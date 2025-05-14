package com.hardi.breedwise.ui.allbreed

import app.cash.turbine.test
import com.hardi.breedwise.data.model.DogBreeds
import com.hardi.breedwise.data.repository.SubBreedRepository
import com.hardi.breedwise.ui.subbreeds.SubBreedViewModel
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
class SubBreedViewModelTest {

    @Mock
    private lateinit var repository: SubBreedRepository

    @Mock
    private lateinit var networkHelper: NetworkHelper

    private lateinit var dispatcher: DispatcherProvider

    private lateinit var viewmodel: SubBreedViewModel

    @Before
    fun setUp() {
        dispatcher = TestDispatcherProvider()
    }

    @Test
    fun get_sub_breed_list_success() {
        runTest {
            val breedName = "test"
            val subBreedList = listOf("abc", "xyz", "pqr")

            doReturn(true)
                .`when`(networkHelper)
                .isInternetConnected()
            doReturn(flowOf(subBreedList))
                .`when`(repository)
                .getSubBreed(breedName)

            viewmodel = SubBreedViewModel(repository, dispatcher, networkHelper)
            viewmodel.getSubBreed(breedName)
            viewmodel.uiState.test {
                assertEquals(UIState.Success(subBreedList), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(repository, times(1)).getSubBreed(breedName)
        }

    }

    @Test
    fun get_sub_breed_failed_test() {
        runTest {
            val breedName = "test"
            val error = IllegalAccessException("Something went wrong")
            doReturn(true)
                .`when`(networkHelper)
                .isInternetConnected()
            doReturn(flow<List<DogBreeds>> {
                throw error
            }).`when`(repository).getSubBreed(breedName)

            viewmodel = SubBreedViewModel(repository, dispatcher, networkHelper)
            viewmodel.getSubBreed(breedName)
            viewmodel.uiState.test {
                assertEquals(UIState.Error(error.toString()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }


            verify(repository, times(1)).getSubBreed(breedName)
        }
    }
}