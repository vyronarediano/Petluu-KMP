package com.petluu.app.feature_home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.petluu.app.feature_home.domain.Pet
import com.petluu.app.feature_home.domain.PetDataSource
import com.petluu.app.feature_home.domain.PetValidator
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author Cedierick Vyron Arediano
 * @since 1.0.0
 */
class HomeVM(
    private val petDataSource: PetDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = combine(
        _state,
        petDataSource.getPets()
    ) { state, pets ->
        state.copy(
            pets = pets,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), HomeState())

    var newPet: Pet? by mutableStateOf(null)
        private set

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnAddNewPetClick -> {
                _state.update {
                    it.copy(
                        isAddPetSheetOpen = true
                    )
                }

                newPet = Pet(
                    id = null,
                    name = "",
                    species = "",
                    breed = "",
                    gender = null,
                    color = "",
                    birthday = "",
                    weight = null,
                    microchipNum = "",
                    microchipDate = null,
                    ownerId = null,
                    photoBytes = null
                )
            }

            HomeEvent.DismissPet -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isSelectedPetSheetOpen = false,
                        isAddPetSheetOpen = false,
                        nameError = null,
                        speciesError = null,
                        breedError = null
                    ) }
                    delay(300L)
                    newPet = null
                    _state.update {
                        it.copy(
                            selectedPet = null
                        )
                    }
                }
            }

            HomeEvent.SavePet -> {
                newPet?.let { pet ->
                    val result = PetValidator.validatePet(pet)
                    val errors = listOfNotNull(
                        result.nameError,
                        result.speciesError,
                        result.breedError
                    )

                    if (errors.isEmpty()) {
                        // SUCCESS: PROCEED TO SAVE
                        _state.update {
                            it.copy(
                                isAddPetSheetOpen = false,
                                nameError = null,
                                speciesError = null,
                                breedError = null
                            )
                        }
                        viewModelScope.launch {
                            petDataSource.insertPet(pet)
                            delay(300L)
                            newPet = null
                        }

                    } else {
                        // FAILED
                        _state.update {
                            it.copy(
                                nameError = result.nameError,
                                speciesError = result.speciesError,
                                breedError = result.breedError,
                            )
                        }
                    }
                }
            }

            is HomeEvent.EditPet -> {
                _state.update {
                    it.copy(
                        selectedPet = null,
                        isAddPetSheetOpen = true,
                        isSelectedPetSheetOpen = false
                    )
                }
                newPet = event.pet
            }

            HomeEvent.DeletePet -> {
                viewModelScope.launch {
                    _state.value.selectedPet?.id?.let { petId ->
                        _state.update {
                            it.copy(
                                isSelectedPetSheetOpen = false
                            )
                        }
                        petDataSource.deletePet(petId)
                        delay(300L)
                        _state.update {
                            it.copy(
                                selectedPet = null
                            )
                        }
                    }
                }
            }

            is HomeEvent.SelectPet -> {
                _state.update {
                    it.copy(
                        selectedPet = event.pet,
                        isSelectedPetSheetOpen = true
                    )
                }
            }

            is HomeEvent.OnNameChanged -> {
                newPet = newPet?.copy(name = event.value)
            }

            is HomeEvent.OnSpeciesChanged -> {
                newPet = newPet?.copy(species = event.value)
            }

            is HomeEvent.OnBreedChanged -> {
                newPet = newPet?.copy(breed = event.value)
            }

            is HomeEvent.OnGenderChanged -> {
                //newPet = newPet?.copy(gender = Gender.valueOf(event.value.uppercase()))
            }

            is HomeEvent.OnColorChanged -> {
                newPet = newPet?.copy(color = event.value)
            }

            is HomeEvent.OnBirthdayChanged -> {
                newPet = newPet?.copy(birthday = event.value)
            }

            is HomeEvent.OnMicrochipNumChanged -> {
                newPet = newPet?.copy(microchipNum = event.value)
            }

            is HomeEvent.OnMicrochipDateChanged -> {
                newPet = newPet?.copy(microchipDate = event.value)
            }

            is HomeEvent.OnPhotoPicked -> {
                newPet = newPet?.copy(photoBytes = event.bytes)
            }

            else -> Unit
        }
    }
}