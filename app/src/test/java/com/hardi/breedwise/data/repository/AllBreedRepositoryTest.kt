package com.hardi.breedwise.data.repository

import android.accounts.NetworkErrorException
import app.cash.turbine.test
import com.hardi.breedwise.data.api.ApiService
import com.hardi.breedwise.data.model.AllBreedResult
import com.hardi.breedwise.data.model.DogBreeds
import com.hardi.breedwise.data.roomdatabase.DatabaseService
import com.hardi.breedwise.data.roomdatabase.entity.toDogBreedsEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AllBreedRepositoryTest {

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var databaseService: DatabaseService


    private lateinit var repository: AllBreedRepository

    @Before
    fun setUp() {
        repository = AllBreedRepository(apiService, databaseService)
    }

    @Test
    fun fetch_all_breed_success() {
        runTest {
            val status = "success"
            val message = mapOf("test1" to listOf("1", "2"), "test2" to emptyList())
            val result = AllBreedResult(message, status)

            val dogBreedList = listOf(
                DogBreeds("test1", listOf("1", "2"))
            )

            val dogbreedEntity = dogBreedList.map { it.toDogBreedsEntity() }


            doReturn(result).`when`(apiService).fetchAllBreed()
            doNothing().`when`(databaseService).deleteAllAndInsertAll(dogbreedEntity)
            doReturn(flowOf(dogbreedEntity)).`when`(databaseService).getDogBreeds()

            repository.getAllBreed().test {
                assertEquals(dogBreedList, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(apiService, times(1)).fetchAllBreed()
            verify(databaseService, times(1)).deleteAllAndInsertAll(dogbreedEntity)
        }
    }


    @Test
    fun fetch_all_breed_failed() {
        runTest {
            val error = "UnknownHostException"

            doThrow(RuntimeException(error)).`when`(apiService).fetchAllBreed()

            repository.getAllBreed().test{
                assertEquals(error,awaitError().message)
                cancelAndIgnoreRemainingEvents()
            }

            verify(apiService, times(1)).fetchAllBreed()

        }
    }
}