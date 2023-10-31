package com.petluu.app.feature_home.presentation

import com.petluu.app.feature_home.domain.Pet

/**
 * @author Cedierick Vyron Arediano
 * @since 1.0.0
 */
data class HomeState(
    val pets: List<Pet> = emptyList(),
    val selectedPet: Pet? = null,
    val isAddPetSheetOpen: Boolean = false,
    val isPetSelected: Boolean = false,
    val nameError: String? = null,
    val speciesError: String? = null,
    val breedError: String? = null
)

