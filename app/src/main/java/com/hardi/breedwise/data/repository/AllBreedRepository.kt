package com.hardi.breedwise.data.repository

import com.hardi.breedwise.data.api.ApiService
import com.hardi.breedwise.data.model.DogBreeds
import com.hardi.breedwise.data.roomdatabase.DatabaseService
import com.hardi.breedwise.data.roomdatabase.entity.DogBreedsEntity
import com.hardi.breedwise.data.roomdatabase.entity.toDogBreedsEntity
import com.hardi.breedwise.data.roomdatabase.entity.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AllBreedRepository @Inject constructor(
    private val apiService: ApiService,
    private val databaseService: DatabaseService) {

    fun getAllBreed(): Flow<List<DogBreeds>> {
        return flow {
            val response = apiService.fetchAllBreed()
            val allBreedList =
                response.message.filterValues { it.isNotEmpty() }.map { (breed, subBreeds) ->
                    DogBreeds(breed, subBreeds)
                }

            emit(allBreedList)
        }.map {
            it.map { dogBreeds -> dogBreeds.toDogBreedsEntity() }
        }.flatMapConcat { breedEntity ->
            flow {
                emit(databaseService.deleteAllAndInsertAll(breedEntity))
            }
        }.flatMapConcat {
            databaseService.getDogBreeds().map { breedEntityList ->
                breedEntityList.map { it.toDomain() }
            }
        }
    }

    fun getAllBreedFromDB(): Flow<List<DogBreeds>> {
        return databaseService.getDogBreeds().map { breedEntityList ->
            breedEntityList.map { it.toDomain() }
        }
    }

}