package com.now.desafio.android.data.entities

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Keep
@IgnoreExtraProperties
data class User(
    val name: String? = null,
    val email: String? = null,
    val dateNascimento: Date? = null,
    val password: String? = null,
    var favorites: MutableList<Artist>? = null
) : Parcelable
