package com.hardi.breedwise.data.roomdatabase

import com.hardi.breedwise.data.roomdatabase.entity.DogBreedsEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseService {
    fun getDogBreeds(): Flow<List<DogBreedsEntity>>

    fun deleteAllAndInsertAll(dogBreedsEntity: List<DogBreedsEntity>)
}
