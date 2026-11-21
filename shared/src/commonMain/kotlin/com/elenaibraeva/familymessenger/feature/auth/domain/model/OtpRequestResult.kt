package com.elenaibraeva.familymessenger.feature.auth.domain.model

sealed interface OtpRequestResult {
    data object Sent : OtpRequestResult

    data class CooldownActive(val remainingSeconds: Int) : OtpRequestResult
}
