package com.picpay.desafio.android.data.dao.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")

data class UserTable(
    @PrimaryKey val id: Int,
    @ColumnInfo var username: String,
    @ColumnInfo val img: String,
    @ColumnInfo val name: String,
    @ColumnInfo val favorite: Boolean = false
    )