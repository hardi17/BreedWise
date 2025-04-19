package com.hardi.breedwise.ui.allbreed

import com.hardi.breedwise.data.repository.AllBreedRepository
import com.hardi.breedwise.utils.DispatcherProvider
import com.hardi.breedwise.utils.TestDispatcherProvider
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

    private lateinit var dispatcher: DispatcherProvider

    private lateinit var viewModel: AllBreedViewmodel

    @Before
    fun setUp() {
        dispatcher = TestDispatcherProvider()
    }

    @Test
    fun get_all_breed_succeefully_test() {
        runTest {
            val result = listOf("a", "b", "c")

            doReturn(flowOf(result)).`when`(repository).getAllBreed()

            viewModel = AllBreedViewmodel(repository, dispatcher)

            viewModel.loadAllBreed()

            assertEquals(result, viewModel.breeds.value)

            verify(repository, times(1)).getAllBreed()
        }
    }

    @Test
    fun get_all_breed_failed_test() {
        runTest {
            val error = ""
            doReturn(flow<String> {
                error
            }).`when`(repository).getAllBreed()

            viewModel = AllBreedViewmodel(repository, dispatcher)

            viewModel.loadAllBreed()

            assertEquals(error, viewModel.error.value)

            verify(repository, times(1)).getAllBreed()
        }

    }


}