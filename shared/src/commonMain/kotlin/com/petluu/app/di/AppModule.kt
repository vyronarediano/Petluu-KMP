package com.petluu.app.di

import com.petluu.app.feature_pets.domain.PetDataSource

expect class AppModule {
    val petDataSource: PetDataSource
}