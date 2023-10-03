package com.petluu.app.feature_pets.presentation

import com.petluu.app.feature_pets.domain.Pet

/**
 * @author Cedierick Vyron Arediano
 * @since 1.0.0
 */
sealed interface PetListEvent {
    object OnAddNewPetClick: PetListEvent
    object DismissPet: PetListEvent
    data class OnNameChanged(val value: String): PetListEvent
    data class OnSpeciesChanged(val value: String): PetListEvent
    data class OnBreedChanged(val value: String): PetListEvent
    data class OnGenderChanged(val value: String): PetListEvent
    data class OnColorChanged(val value: String): PetListEvent
    data class OnBirthdayChanged(val value: String): PetListEvent
    data class OnMicrochipNumChanged(val value: String): PetListEvent
    data class OnMicrochipDateChanged(val value: String): PetListEvent
    class OnPhotoPicked(val bytes: ByteArray): PetListEvent
    object OnAddPhotoClicked: PetListEvent
    object SavePet: PetListEvent
    data class SelectPet(val pet: Pet): PetListEvent
    data class EditPet(val pet: Pet): PetListEvent
    object DeletePet: PetListEvent
}
