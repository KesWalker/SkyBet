package com.sky.bet.horses

import com.sky.bet.horses.repo.RacesRepo
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class RepoUnitTests {

    @Test
    fun `Repo getAll() returns array with at least one element`(){
        runBlocking {
            val allRaces = RacesRepo().getAll()
            Assert.assertEquals(allRaces.size > 0,true)
        }
    }

    @Test
    fun `Repo getAll() returns list where the first race has a valid name`(){
        runBlocking {
            val allRaces = RacesRepo().getAll()
            val firstRaceName = allRaces[0].race_summary.name
            Assert.assertEquals(firstRaceName.trim().length > 0, true)
        }
    }
}