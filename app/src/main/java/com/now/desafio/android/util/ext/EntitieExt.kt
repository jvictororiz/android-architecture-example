package com.now.desafio.android.util.ext

import android.text.TextUtils
import android.util.Patterns
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.now.desafio.android.R
import com.now.desafio.android.data.entities.Artist
import com.picpay.desafio.android.data.dao.entities.UserTable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


val navStart = NavOptions.Builder()
    .setEnterAnim(R.anim.enter_from_right)
    .setExitAnim(R.anim.exit_to_left)
    .setPopEnterAnim(R.anim.enter_from_left)
    .setPopExitAnim(R.anim.exit_to_right).build()


fun NavController.navigateWithAnimation(destinationId: Int) {
    this.navigate(destinationId, null, navStart)
}

fun NavController.navigateWithAnimation(navDirections: NavDirections) {
    this.navigate(navDirections, navStart)
}

fun String.toDate(pattern: String = "dd/MM/yyyy"): Date {
    val format = SimpleDateFormat(pattern, LOCALE_PT_BR)
    return try {
        format.parse(this)?:Date()
    } catch (e: ParseException) {
        throw Exception()
    }
}

val LOCALE_PT_BR = Locale("pt", "BR")
fun Date.dateToString(pattern: String = "dd/MM/yyyy"): String? {
    return SimpleDateFormat(pattern, LOCALE_PT_BR).format(this)


}
fun String.isValidEmail(): Boolean {
    return if (TextUtils.isEmpty(this)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}

fun Artist.toUserTable(): UserTable {
    return UserTable(id, username, img, name, favorited)
}

fun UserTable.toUser(): Artist {
    return Artist(img, name, id, favorite, username)
}

@ExperimentalCoroutinesApi
inline fun <reified T> DatabaseReference.listen(): Flow<T?> =
    callbackFlow {
        val valueListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                close()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    val value = dataSnapshot.getValue(T::class.java)
                    offer(value)
                } catch (exp: Exception) {
                    if (!isClosedForSend) offer(null)
                }
            }
        }
        addListenerForSingleValueEvent(valueListener)
    }