package com.hardi.breedwise.data.repository

import com.hardi.breedwise.data.api.ApiService
import com.hardi.breedwise.data.model.DogBreeds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AllBreedRepository @Inject constructor(private val apiService: ApiService) {

    fun getAllBreed(): Flow<List<DogBreeds>> {
        return flow {
            val response = apiService.fetchAllBreed()
            val allBreedList = response.message.map { (breed, subBreeds) ->
                DogBreeds(breed, subBreeds)
            }
            emit(allBreedList)
        }
    }
}