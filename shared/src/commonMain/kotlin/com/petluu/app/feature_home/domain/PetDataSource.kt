package com.petluu.app.feature_home.domain

import kotlinx.coroutines.flow.Flow

interface PetDataSource {

    fun getPets(): Flow<List<Pet>>

    suspend fun insertPet(pet: Pet)

    suspend fun deletePet(id: Long)

}