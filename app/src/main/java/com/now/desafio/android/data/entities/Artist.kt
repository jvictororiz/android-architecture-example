package com.now.desafio.android.data.entities

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
@IgnoreExtraProperties
data class Artist(
    val img: String = "",
    val name: String = "",
    val id: Int = -1,
    var favorited :Boolean = false,
    val username: String =""
) : Parcelable