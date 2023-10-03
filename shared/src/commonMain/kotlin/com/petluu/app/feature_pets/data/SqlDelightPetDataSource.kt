package com.petluu.app.feature_pets.data

import com.petluu.app.core.data.ImageStorage
import com.petluu.app.database.PetluuDatabase
import com.petluu.app.feature_pets.domain.Gender
import com.petluu.app.feature_pets.domain.Pet
import com.petluu.app.feature_pets.domain.PetDataSource
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.datetime.Clock

class SqlDelightPetDataSource(
    db: PetluuDatabase,
    private val imageStorage: ImageStorage
) : PetDataSource {

    private val queries = db.petluuQueries

    override fun getPets(): Flow<List<Pet>> {
        return queries
            .getPets()
            .asFlow()
            .mapToList()
            .map { contactEntities ->
                supervisorScope {
                    contactEntities
                        .map {
                            async { it.toPet(imageStorage) }
                        }
                        .map { it.await() }
                }
            }
    }

    override suspend fun insertPet(pet: Pet) {
        val imagePath = pet.photoBytes?.let {
            imageStorage.saveImage(it)
        }
        queries.insertPetEntity(
            id = pet.id,
            name = pet.name,
            species = pet.species,
            breed = pet.breed,
            gender = Gender.MALE.name,
            color = pet.color.orEmpty(),
            birthday = pet.birthday.orEmpty(),
            weight = pet.weight,
            microchipNum = pet.microchipNum.orEmpty(),
            microchipDate = pet.microchipDate.orEmpty(),
            ownerId = pet.ownerId,
            createdAt = Clock.System.now().toEpochMilliseconds(),
            imagePath = imagePath
        )
    }

    override suspend fun deletePet(id: Long) {
        val entity = queries.getPetById(id).executeAsOne()
        entity.imagePath?.let {
            imageStorage.deleteImage(it)
        }
        queries.deletePet(id)
    }


}