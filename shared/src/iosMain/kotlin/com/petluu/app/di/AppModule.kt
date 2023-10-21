package com.petluu.app.di

import com.petluu.app.feature_home.data.SqlDelightPetDataSource
import com.petluu.app.feature_home.domain.PetDataSource
import com.petluu.app.core.data.DatabaseDriverFactory
import com.petluu.app.core.data.ImageStorage
import com.petluu.app.database.PetluuDatabase

actual class AppModule {

    actual val petDataSource: PetDataSource by lazy {
        SqlDelightPetDataSource(
            db = PetluuDatabase.invoke(
                driver = DatabaseDriverFactory().create()
            ),
            imageStorage = ImageStorage()
        )
    }
}