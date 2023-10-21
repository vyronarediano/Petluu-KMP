package com.petluu.app.feature_home.domain

/**
 * @author Cedierick Vyron Arediano
 * @since 1.0.0
 */
object PetValidator {

    fun validatePet(pet: Pet): ValidationResult {
        var result = ValidationResult()

        if (pet.name.isBlank()) {
            result = result.copy(nameError = "Name can't be empty.")
        }

        if (pet.species.isBlank()) {
            result = result.copy(speciesError = "Species can't be empty.")
        }

        if (pet.breed.isBlank()) {
            result = result.copy(breedError = "Breed can't be empty.")
        }

        return result
    }

    data class ValidationResult(
        val nameError: String? = null,
        val speciesError: String? = null,
        val breedError: String? = null
    )
}