package com.hardi.breedwise.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface AllBreedRepository {
    fun getAllBreed() : Flow<List<String>>
//        return flow {
//            val list = listOf("a","b","c")
//            emit(list)
//        }
  //  }
}