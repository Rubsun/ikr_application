package com.dimmension.network.data.randomuser

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RandomUserResponse(
    @SerialName("results")
    val results: List<RandomUser>
)

@Serializable
internal data class RandomUser(
    @SerialName("name")
    val name: RandomUserName
)

@Serializable
internal data class RandomUserName(
    @SerialName("first")
    val first: String,
    @SerialName("last")
    val last: String
)

