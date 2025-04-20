package com.hardi.breedwise.data.repository

import app.cash.turbine.test
import com.hardi.breedwise.data.api.ApiService
import com.hardi.breedwise.data.model.AllBreedResult
import com.hardi.breedwise.data.model.DogBreeds
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
class AllBreedRepositoryTest {

    @Mock
    private lateinit var apiService: ApiService


    private lateinit var repository: AllBreedRepository

    @Before
    fun setUp() {
        repository = AllBreedRepository(apiService)
    }

    @Test
    fun fetch_all_breed_success() {
        runTest {
            val status = "success"
            val message = mapOf("test" to listOf("1", "2"))
            val result = AllBreedResult(message, status)

            doReturn(result).`when`(apiService).fetchAllBreed()

            val dogBreed = result.message.map {
                DogBreeds(it.key, it.value)
            }
            repository.getAllBreed().test {
                assertEquals(dogBreed, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(apiService, times(1)).fetchAllBreed()
        }
    }
}