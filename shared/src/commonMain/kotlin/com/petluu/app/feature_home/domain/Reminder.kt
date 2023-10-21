package com.petluu.app.feature_home.domain

data class Reminder(
    val id: Long,
    val title: String,
    val date: Long,
    val recordType: String,
    val petName: String,
)
