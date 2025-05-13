package com.hardi.breedwise.ui.allbreed

import app.cash.turbine.test
import com.hardi.breedwise.data.repository.SubBreedRepository
import com.hardi.breedwise.ui.subbreeds.SubBreedViewModel
import com.hardi.breedwise.utils.DispatcherProvider
import com.hardi.breedwise.utils.TestDispatcherProvider
import com.hardi.breedwise.utils.UIState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SubBreedViewModelTest {

    @Mock
    private lateinit var repository: SubBreedRepository

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

            doReturn(flowOf( subBreedList))
                .`when`(repository)
                .getSubBreed(breedName)

            viewmodel = SubBreedViewModel(repository, dispatcher)
            viewmodel.getSubBreed(breedName)
            viewmodel.uiState.test{
                assertEquals(UIState.Success(subBreedList), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

        }
    }
}