package com.elenaibraeva.familymessenger.feature.auth.presentation.mapper

import com.elenaibraeva.familymessenger.feature.auth.domain.model.AuthError
import com.elenaibraeva.familymessenger.generated.resources.Res
import com.elenaibraeva.familymessenger.generated.resources.auth_error_email_rate_limit
import com.elenaibraeva.familymessenger.generated.resources.auth_error_invalid_email
import com.elenaibraeva.familymessenger.generated.resources.auth_error_invalid_otp
import com.elenaibraeva.familymessenger.generated.resources.auth_error_network
import com.elenaibraeva.familymessenger.generated.resources.auth_error_request_rate_limit
import com.elenaibraeva.familymessenger.generated.resources.auth_error_server
import com.elenaibraeva.familymessenger.generated.resources.auth_error_unknown
import org.jetbrains.compose.resources.StringResource

internal fun AuthError.toMessageResource(): StringResource = when (this) {
    AuthError.EMAIL_RATE_LIMIT ->
        Res.string.auth_error_email_rate_limit

    AuthError.REQUEST_RATE_LIMIT ->
        Res.string.auth_error_request_rate_limit

    AuthError.INVALID_OR_EXPIRED_OTP ->
        Res.string.auth_error_invalid_otp

    AuthError.INVALID_EMAIL ->
        Res.string.auth_error_invalid_email

    AuthError.NETWORK ->
        Res.string.auth_error_network

    AuthError.SERVER ->
        Res.string.auth_error_server

    AuthError.UNKNOWN ->
        Res.string.auth_error_unknown
}
