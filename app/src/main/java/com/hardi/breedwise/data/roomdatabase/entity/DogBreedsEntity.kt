package com.hardi.breedwise.data.roomdatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breed")
data class DogBreedsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "breed_id")
    val id: Int = 0,
    @ColumnInfo("breed")
    val breed: String,
    @ColumnInfo("subBreedList")
    val subBreedList: String? = ""
)
