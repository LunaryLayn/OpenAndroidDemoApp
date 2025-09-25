package com.hugopolog.domain.entities.config.error

sealed interface DataError : RootError {

    //TODO() Right now those are just examples, we need to define the actual errors
    sealed class Generic(val code: Int? = null) : DataError {
        data object NetworkError : Generic(null)
        data object ServerError : Generic(500)
        data object UnknownError : Generic(600)
        data object NoData : Generic(null)
        data object BadRequest : Generic(400)
        data object Unauthorized : Generic(401)
        data object Forbidden : Generic(403)
        data object NotFound : Generic(404)
        data object Conflict : Generic(409)
        data object Timeout : Generic(null)

        // Errores internos de parsing / mapping. Como ejemplo asignamos "550" como estándar
        data object DeserializationError : Generic(550)
        data object MappingError : Generic(551)
        data object Canceled : Generic(null)
    }


    sealed class ExampleSectionErrors : Generic() {
        data object TestError : ExampleSectionErrors()
    }
}
