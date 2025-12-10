package dog.dohodyaga.gaf.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {

    private val dogApiService = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DogApiService::class.java)

    suspend fun fetchDogImage(): DogImageDto {
        return dogApiService.getRandomDogImage()
    }
}