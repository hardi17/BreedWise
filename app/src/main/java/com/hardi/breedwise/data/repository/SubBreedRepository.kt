package com.hardi.breedwise.data.repository

import com.hardi.breedwise.data.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubBreedRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getSubBreed(breedName: String): Flow<List<String>> {
        return flow {
            val result = apiService.fetchSubBreed(breedName)
            val subBreedList = result.message.filter { it.isNotEmpty() }
            emit(subBreedList)
        }
    }
}