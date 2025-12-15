package com.example.ikr_application.dimmension.domain.models

data class NameDisplayModel(
    val displayName: String,
    val shortName: String,
    val initials: String,
) {
    // URL для загрузки аватарки через UI Avatars API (бесплатный, без токена)
    val avatarUrl: String
        get() = "https://ui-avatars.com/api/?name=${displayName.replace(" ", "+")}&size=128&background=random"
}

