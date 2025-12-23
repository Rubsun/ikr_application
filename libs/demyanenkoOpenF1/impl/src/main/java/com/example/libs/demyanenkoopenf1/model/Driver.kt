package com.example.libs.demyanenkoopenf1.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    @SerialName("driver_number")
    val driverNumber: Int,
    @SerialName("broadcast_name")
    val broadcastName: String?,
    @SerialName("full_name")
    val fullName: String?,
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("name_acronym")
    val nameAcronym: String?,
    @SerialName("team_name")
    val teamName: String?,
    @SerialName("team_colour")
    val teamColour: String?,
    @SerialName("country_code")
    val countryCode: String?,
    @SerialName("headshot_url")
    val headshotUrl: String?,
    @SerialName("session_key")
    val sessionKey: Long?
)
