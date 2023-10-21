package com.petluu.app.di

import android.content.Context
import com.petluu.app.core.data.DatabaseDriverFactory
import com.petluu.app.core.data.ImageStorage
import com.petluu.app.database.PetluuDatabase
import com.petluu.app.feature_home.data.SqlDelightPetDataSource
import com.petluu.app.feature_home.domain.PetDataSource

actual class AppModule(
    private val context: Context
) {

    actual val petDataSource: PetDataSource by lazy {
        SqlDelightPetDataSource(
            db = PetluuDatabase(
                driver = DatabaseDriverFactory(context).create(),
            ),
            imageStorage = ImageStorage(context)
        )
    }
}