package com.petluu.app.core.data

import android.content.Context
import com.petluu.app.database.PetluuDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            PetluuDatabase.Schema,
            context,
            "petluu.db"
        )
    }
}