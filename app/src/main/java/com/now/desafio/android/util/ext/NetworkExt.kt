package com.now.desafio.android.util.ext


import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.now.desafio.android.data.entities.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.net.ConnectException


suspend fun <T> Call<T>.backgroundCall(dispatcher: CoroutineDispatcher): Result<T?> {
    return withContext(context = dispatcher) {
        try {
            val response = this@backgroundCall.execute()
            if (response.isSuccessful) {
                Result.success(response.body(), response.code())
            } else {
                val error =
                    Gson().fromJson(response.errorBody()?.string(), ErrorDefault::class.java)
                Result.error(error.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is ConnectException || e is java.net.UnknownHostException) {
                Result.error<T?>(ConnectException("Seu dispositivo está sem internet."))
            } else {
                Result.error(e)
            }
        }
    }
}

suspend inline fun <reified T> DatabaseReference.backgroundCall(chield: String): Result<out T?> {
    return withContext(Dispatchers.IO) {
        val tcs: TaskCompletionSource<T> = TaskCompletionSource()
        child(converterField(chield)).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                tcs.setException(java.lang.Exception("Não foi possível conectar com o servidor, por favor, tente mais tarde"))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tcs.setResult(dataSnapshot.getValue(T::class.java))
            }
        })
        val t: Task<T> = tcs.task
        Tasks.await<T>(t)

        when {
            t.isSuccessful -> {
                return@withContext Result.success(t.result)

            }
            t.exception != null -> {
                return@withContext Result.error<T>(t.exception!!.message)
            }
            else -> {
                return@withContext Result.error<T>("Erro interno")
            }
        }
    }
}

suspend inline fun <reified T> DatabaseReference.backgroundSave(key: String, data: T): Result<out T?> {
    return withContext(Dispatchers.IO) {
        val tcs: TaskCompletionSource<T> = TaskCompletionSource()
        child(converterField(key)).setValue(data).addOnSuccessListener {
            tcs.setResult(data)
        }.addOnFailureListener {
            tcs.setException(java.lang.Exception("Não foi possível conectar com o servidor, por favor, tente mais tarde"))
        }
        val t: Task<T> = tcs.task
        Tasks.await<T>(t)

        when {
            t.isSuccessful -> {
                return@withContext Result.success(t.result)

            }
            t.exception != null -> {
                return@withContext Result.error<T>(t.exception!!.message)
            }
            else -> {
                return@withContext Result.error<T>("Erro interno")
            }
        }
    }
}

fun converterField(key: String): String {
    return key.replace(".", ",")
}

data class ErrorDefault(val message: String)

