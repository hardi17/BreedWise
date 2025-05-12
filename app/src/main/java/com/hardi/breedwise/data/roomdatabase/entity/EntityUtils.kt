package com.hardi.breedwise.data.roomdatabase.entity

import com.hardi.breedwise.data.model.DogBreeds

fun DogBreeds.toDogBreedsEntity() : DogBreedsEntity{
    return DogBreedsEntity(
        breed = breed,
        subBreedList = subBreedList?.joinToString(",")
    )
}

fun DogBreedsEntity.toDomain(): DogBreeds {
    return DogBreeds(
        breed = this.breed,
        subBreedList = this.subBreedList?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() }
    )
}