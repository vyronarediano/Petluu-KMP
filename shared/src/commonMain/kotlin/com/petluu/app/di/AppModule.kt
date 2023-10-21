package com.petluu.app.di

import com.petluu.app.feature_home.domain.PetDataSource

expect class AppModule {
    val petDataSource: PetDataSource
}