package com.hardi.breedwise.ui.breedImages

import app.cash.turbine.test
import com.hardi.breedwise.data.model.DogBreeds
import com.hardi.breedwise.data.repository.BreedImagesRepository
import com.hardi.breedwise.ui.subbreeds.SubBreedViewModel
import com.hardi.breedwise.utils.DefaultDispatcherProvider
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
class BreedImagesViewModelTest {

    @Mock
    private lateinit var repository: BreedImagesRepository

    @Mock
    private lateinit var networkHelper: NetworkHelper

    private lateinit var viewmodel: BreedImagesViewModel

    private lateinit var dispatcherProvider: DispatcherProvider


    @Before
    fun setUp(){
        dispatcherProvider = TestDispatcherProvider()
    }

    @Test
    fun get_breed_image_success(){
        runTest {
            val breedName = "test"
            val subbreedName = "test"
            val imgList = listOf("url1", "url2", "url3")

            doReturn(true)
                .`when`(networkHelper)
                .isInternetConnected()
            doReturn(flowOf(imgList))
                .`when`(repository)
                .getBreedImages(breedName, subbreedName)

            viewmodel = BreedImagesViewModel(repository, dispatcherProvider, networkHelper)
            viewmodel.loadBreedImages(breedName, subbreedName)
            viewmodel.uiState.test {
                assertEquals(UIState.Success(imgList), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(repository, times(1)).getBreedImages(breedName, subbreedName)
        }
    }

    @Test
    fun get_breed_image_failed(){
        runTest {
            val breedName = "test"
            val subbreedName = "test"
            val error = IllegalAccessException("Something went wrong")
            doReturn(true)
                .`when`(networkHelper)
                .isInternetConnected()
            doReturn(flow<List<DogBreeds>> {
                throw error
            }).`when`(repository).getBreedImages(breedName, subbreedName)

            viewmodel = BreedImagesViewModel(repository, dispatcherProvider, networkHelper)
            viewmodel.loadBreedImages(breedName, subbreedName)
            viewmodel.uiState.test {
                assertEquals(UIState.Error(error.toString()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }


            verify(repository, times(1)).getBreedImages(breedName, subbreedName)
        }
    }
}