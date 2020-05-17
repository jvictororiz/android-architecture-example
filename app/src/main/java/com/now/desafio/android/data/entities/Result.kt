package com.now.desafio.android.data.entities


data class Result<T>(var data: T? = null, val throwable: Throwable? = null, val code: Int? = null) {

    fun isSuccessful() = throwable == null

    fun containsError() = throwable != null

    fun errorMessage() = throwable?.message ?: ""

    fun isCacheSuccessful() = !isSuccessful() && data != null

    companion object {

        /**
         * Cria um resultado de erro a partir de uma mensagem.
         */
        fun <T> error(msg: String?, code: Int? = null) =
            Result<T>(
                throwable = Throwable(msg),
                code = code
            )

        /**
         * Cria uma mensagem de erro a partir de uma exceção.
         */
        fun <T> error(throwable: Throwable, code: Int? = null) =
            Result<T>(
                throwable = throwable,
                code = code
            )

        /**
         * Cria um resultado de sucesso.
         */
        fun <T> success(data: T, code: Int? = null) =
            Result(
                data = data,
                code = code
            )

        fun <T> cacheSuccess(data: T, throwable: Throwable) =
            Result(
                data = data,
                throwable = throwable
            )

    }
}