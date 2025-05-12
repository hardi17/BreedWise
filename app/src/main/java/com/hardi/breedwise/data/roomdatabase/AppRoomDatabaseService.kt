package com.hardi.breedwise.data.roomdatabase

import com.hardi.breedwise.data.roomdatabase.entity.DogBreedsEntity
import kotlinx.coroutines.flow.Flow

class AppRoomDatabaseService(
    private val appRoomDataBase: AppRoomDataBase
) : DatabaseService {

    override fun getDogBreeds(): Flow<List<DogBreedsEntity>> {
        return appRoomDataBase.dogBreedsDao().getAll()
    }

    override fun deleteAllAndInsertAll(dogBreedsEntity: List<DogBreedsEntity>) {
        return appRoomDataBase.dogBreedsDao().deleteAllAndInsertAll(dogBreedsEntity)
    }

}