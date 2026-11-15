package com.elenaibraeva.familymessenger.feature.auth.domain.model

class AuthException(val error: AuthError, cause: Throwable? = null) : Exception(error.name, cause)
