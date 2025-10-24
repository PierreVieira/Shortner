package com.pierre.shortner.core.room_provider.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "links",
    indices = [Index(value = ["shortedLink"], unique = true)]
)
data class LinkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val shortedLink: String,
    val originalLink: String,
    val alias: String,
)
