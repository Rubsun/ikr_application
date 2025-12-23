package com.example.libs.demyanenkoopenf1.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarData(
    @SerialName("driver_number")
    val driverNumber: Int,
    @SerialName("session_key")
    val sessionKey: Long,
    @SerialName("date")
    val date: String?,
    @SerialName("speed")
    val speed: Int?,
    @SerialName("rpm")
    val rpm: Int?,
    @SerialName("drs")
    val drs: Int?,
    @SerialName("throttle")
    val throttle: Int?,
    @SerialName("brake")
    val brake: Int?,
    @SerialName("x")
    val x: Double?,
    @SerialName("y")
    val y: Double?,
    @SerialName("z")
    val z: Double?
)
