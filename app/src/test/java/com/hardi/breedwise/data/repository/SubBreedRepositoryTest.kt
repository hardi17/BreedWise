package com.hardi.breedwise.data.repository

import app.cash.turbine.test
import com.hardi.breedwise.data.api.ApiService
import com.hardi.breedwise.data.model.SubBreedResult
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SubBreedRepositoryTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var repository: SubBreedRepository

    @Before
    fun setUp(){
        repository = SubBreedRepository(apiService)
    }

    @Test
    fun fetch_sub_breed_success(){
        runTest {
            val breedName = "test"
            val subBreedList = listOf("abc", "xyz", "pqr")
            val status = "success"
            val subBreedResult = SubBreedResult(subBreedList, status)

            doReturn(subBreedResult)
                .`when`(apiService)
                .fetchSubBreed(breedName)

            repository.getSubBreed(breedName).test {
                assertEquals(subBreedList, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(apiService, times(1)).fetchSubBreed(breedName)
        }
    }

    @Test
    fun fetch_sub_breed_failed() {
        runTest {
            val breedName = "test"
            val error = "UnknownHostException"

            doThrow(RuntimeException(error))
                .`when`(apiService)
                .fetchSubBreed(breedName)

            repository.getSubBreed(breedName).test{
                assertEquals(error,awaitError().message)
                cancelAndIgnoreRemainingEvents()
            }

            verify(apiService, times(1)).fetchSubBreed(breedName)

        }
    }
}