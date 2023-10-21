package com.petluu.app.feature_home.presentation

import com.petluu.app.feature_home.domain.Pet

/**
 * @author Cedierick Vyron Arediano
 * @since 1.0.0
 */
sealed interface HomeEvent {
    object OnAddNewPetClick: HomeEvent
    object DismissPet: HomeEvent
    data class OnNameChanged(val value: String): HomeEvent
    data class OnSpeciesChanged(val value: String): HomeEvent
    data class OnBreedChanged(val value: String): HomeEvent
    data class OnGenderChanged(val value: String): HomeEvent
    data class OnColorChanged(val value: String): HomeEvent
    data class OnBirthdayChanged(val value: String): HomeEvent
    data class OnMicrochipNumChanged(val value: String): HomeEvent
    data class OnMicrochipDateChanged(val value: String): HomeEvent
    class OnPhotoPicked(val bytes: ByteArray): HomeEvent
    object OnAddPhotoClicked: HomeEvent
    object SavePet: HomeEvent
    data class SelectPet(val pet: Pet): HomeEvent
    data class EditPet(val pet: Pet): HomeEvent
    object DeletePet: HomeEvent
}
