package kw.cube.pokemon.network

import com.sky.bet.horses.model.Race
import retrofit2.http.GET

interface NetworkService {

    @GET("raceData")
    suspend fun getAll(): MutableList<Race>

}