package com.petluu.app.core.data

import com.petluu.app.database.PetluuDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(PetluuDatabase.Schema, "petluu.db")
    }
}