package com.petluu.app.feature_pets.data

import com.petluu.app.core.data.ImageStorage
import com.petluu.app.feature_pets.domain.Gender
import com.petluu.app.feature_pets.domain.Pet
import database.PetEntity

suspend fun PetEntity.toPet(imageStorage: ImageStorage): Pet {
    return Pet(
        id = id,
        name = name,
        species = species,
        breed = breed,
        gender = Gender.MALE,
        color = color,
        birthday = birthday,
        weight = weight,
        microchipNum = microchipNum,
        microchipDate = microchipDate,
        ownerId = ownerId,
        photoBytes = imagePath?.let { imageStorage.getImage(it) }
    )
}