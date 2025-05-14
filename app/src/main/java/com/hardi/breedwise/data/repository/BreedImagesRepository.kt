package com.hardi.breedwise.data.repository

import com.hardi.breedwise.data.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreedImagesRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getBreedImages(breedName: String): Flow<List<String>> {
        return flow {
            val response = apiService.fetchBreedImages(breedName)
            val imgList = response.message
            emit(imgList!!)
        }
    }

}