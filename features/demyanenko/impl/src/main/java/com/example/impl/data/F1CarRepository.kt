package com.example.impl.data

import com.example.impl.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

internal class F1CarRepository(private val prefs: F1Preferences) {
    private val _allCars = MutableStateFlow<List<F1Car>>(getDefaultCars())

    private fun getDefaultCars(): List<F1Car> {
        return listOf(
            F1Car(id = "1", name = "RB21", topSpeed = 365, imageRes = R.drawable.rb21),
            F1Car(id = "2", name = "SF-25", topSpeed = 363, imageRes = R.drawable.sf25),
            F1Car(id = "3", name = "MCL39", topSpeed = 361, imageRes = R.drawable.mcl39),
            F1Car(id = "4", name = "W16", topSpeed = 360, imageRes = R.drawable.w16),
            F1Car(id = "5", name = "AMR25", topSpeed = 358, imageRes = R.drawable.amr25),
            F1Car(id = "6", name = "A525", topSpeed = 356, imageRes = R.drawable.a525),
            F1Car(id = "7", name = "VF-25", topSpeed = 355, imageRes = R.drawable.vf25),
            F1Car(id = "8", name = "C45", topSpeed = 354, imageRes = R.drawable.c45),
            F1Car(id = "9", name = "VCARB02", topSpeed = 353, imageRes = R.drawable.vcarb02),
            F1Car(id = "10", name = "FW47", topSpeed = 352, imageRes = R.drawable.fw47),
            F1Car(id = "11", name = "F1", topSpeed = 350, imageRes = R.drawable.f1)
        )
    }

    fun getF1CarsFlow(searchQuery: String = ""): Flow<List<F1Car>> {
        return _allCars.map { cars ->
            if (searchQuery.isBlank()) cars
            else cars.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }
    }

    suspend fun addF1Car(name: String, sound: String? = null): F1Car {
        val newCar = F1Car(
            name = name,
            sound = sound,
            topSpeed = (320..370).random(),
            imageRes = R.drawable.f1
        )
        _allCars.value = _allCars.value + newCar
        prefs.saveLastCarName(name)
        prefs.saveLastSound(sound)
        return newCar
    }

    fun getLastSound(): String? = prefs.getLastSound()
    fun getLastCarName(): String? = prefs.getLastCarName()
}
