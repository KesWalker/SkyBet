package com.sky.bet.horses.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sky.bet.horses.model.Race
import com.sky.bet.horses.repo.RacesRepo
import com.sky.bet.horses.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class RacesViewModel @Inject constructor(private val repo: RacesRepo) : ViewModel() {

    //list of all races
    private val _races = MutableLiveData<MutableList<Race>>()
    val races : LiveData<MutableList<Race>> = _races

    //selected race
    private val _race = MutableLiveData<Race>()
    val race : LiveData<Race> = _race

    //should an error occur, listeners will be notified & can take the appropriate action
    private val _errorGettingAll = MutableLiveData(false)
    val errorGettingAll : LiveData<Boolean> = _errorGettingAll

    init{
        getAllRaces()
    }

    private fun getAllRaces() {
        viewModelScope.launch {
            try{
                _races.value = ArrayList(
                    repo.getAll()
                    .sortedWith(compareBy { it.race_summary.getDateTime() })
                    .filter { it.race_summary.getDateTime() > Date() }
                )
            }catch (e: Exception){
                _errorGettingAll.value = true
            }
        }
    }

    fun selectRace(race: Race){
        _race.value = race
    }

}