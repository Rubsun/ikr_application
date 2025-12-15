package com.example.ikr_application.demyanenko.data

class F1CarRepository {
    private val carNames = listOf(
        "RB21",
        "Sf-25",
        "MCL39",
        "W16",
        "AMR25",
        "A525",
        "VF-25",
        "Fw47",
        "VCARB02",
        "C45"
    )

    fun getF1Car(text: String?): F1Car {
        return F1Car(name = carNames.random(), sound = text)
    }
}