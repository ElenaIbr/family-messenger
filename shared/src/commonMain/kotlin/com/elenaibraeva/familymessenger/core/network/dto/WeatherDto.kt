package com.elenaibraeva.familymessenger.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDto(
    val current: CurrentWeatherDto,
)

@Serializable
data class CurrentWeatherDto(
    @SerialName("temperature_2m")
    val temperature: Double,
)
