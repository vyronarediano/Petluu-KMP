package com.petluu.app.feature_pets.presentation

import com.petluu.app.feature_pets.domain.Pet

/**
 * @author Cedierick Vyron Arediano
 * @since 1.0.0
 */
data class PetListState(
    val pets: List<Pet> = emptyList(),
    val selectedPet: Pet? = null,
    val isAddPetSheetOpen: Boolean = false,
    val isSelectedPetSheetOpen: Boolean = false,
    val nameError: String? = null,
    val speciesError: String? = null,
    val breedError: String? = null
)

