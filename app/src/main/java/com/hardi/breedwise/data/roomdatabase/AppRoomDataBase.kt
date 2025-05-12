package com.hardi.breedwise.data.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hardi.breedwise.data.roomdatabase.dao.DogBreedsDao
import com.hardi.breedwise.data.roomdatabase.entity.DogBreedsEntity

@Database(entities = [DogBreedsEntity::class], version = 1, exportSchema = false )
abstract class AppRoomDataBase : RoomDatabase(){
    abstract fun dogBreedsDao(): DogBreedsDao
}