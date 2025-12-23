package com.dimmension.api.domain.models

data class NameDisplayModel(
    val displayName: String,
    val shortName: String,
    val initials: String,
) {
    val avatarUrl: String
        get() = "https://ui-avatars.com/api/?name=${displayName.replace(" ", "+")}&size=128&background=random"
}


