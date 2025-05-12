package com.hardi.breedwise.data.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.hardi.breedwise.data.roomdatabase.entity.DogBreedsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DogBreedsDao {

    @Query("SELECT * FROM breed")
    fun getAll(): Flow<List<DogBreedsEntity>>

    @Insert
    fun insertAll(breed: List<DogBreedsEntity>)

    @Query("DELETE FROM breed")
    fun deleteAll()

    @Transaction
    fun deleteAllAndInsertAll(breeds: List<DogBreedsEntity>) {
        deleteAll()
        return insertAll(breeds)
    }

}