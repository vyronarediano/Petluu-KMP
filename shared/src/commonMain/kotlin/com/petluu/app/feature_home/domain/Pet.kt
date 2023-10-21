package com.petluu.app.feature_home.domain

data class Pet(
    val id: Long?,
    val name: String,
    val species: String,
    val breed: String,
    val gender: Gender?,
    val color: String?,
    val birthday: String?,
    val weight: Long?,
    val microchipNum: String?,
    val microchipDate: String?,
    val ownerId: Long?,
    val photoBytes: ByteArray?
)
