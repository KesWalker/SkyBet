package com.sky.bet.horses.repo

import com.google.gson.GsonBuilder
import com.sky.bet.horses.model.Race
import kw.cube.pokemon.network.NetworkService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RacesRepo {

    private val networkService: NetworkService

    init{
        networkService = Retrofit.Builder()
            .baseUrl("https://us-central1-walker-solutions.cloudfunctions.net")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NetworkService::class.java)
    }

    suspend fun getAll() : MutableList<Race> {
        return networkService.getAll()
    }

}