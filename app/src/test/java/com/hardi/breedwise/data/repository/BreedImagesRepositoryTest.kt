package com.hardi.breedwise.data.repository

import app.cash.turbine.test
import com.hardi.breedwise.data.api.ApiService
import com.hardi.breedwise.data.model.BreedImageResult
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
class BreedImagesRepositoryTest {

    @Mock
    private lateinit var repository: BreedImagesRepository

    @Mock
    private lateinit var apiService: ApiService

    @Before
    fun setUp(){
        repository = BreedImagesRepository(apiService)
    }

    @Test
    fun get_breed_image_success(){
        runTest {
            val breedName = "mainBreed/subBreed"
            val status = "success"
            val imgList = listOf("url1","url2", "url3")
            val response = BreedImageResult(imgList, status)

            doReturn(response)
                .`when`(apiService)
                .fetchBreedImages(breedName)

            repository.getBreedImages(breedName).test {
                assertEquals(imgList, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun get_breed_image_failed(){
        runTest {
            val breedName = "mainBreed/subBreed"
            val error = "UnknownHostException"

            doThrow(RuntimeException(error))
                .`when`(apiService)
                .fetchBreedImages(breedName)

            repository.getBreedImages(breedName).test{
                assertEquals(error,awaitError().message)
                cancelAndIgnoreRemainingEvents()
            }

            verify(apiService, times(1)).fetchBreedImages(breedName)
        }
    }
}