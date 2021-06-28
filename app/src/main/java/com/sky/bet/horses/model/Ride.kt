package com.sky.bet.horses.model

import java.lang.NumberFormatException

data class Ride(
    val cloth_number: Int,
    val current_odds: String,
    val formsummary: String,
    val handicap: String,
    val horse: Horse
){
    fun getOddsNum() : Int {
        val numerator = current_odds.split("/")[0].toInt()
        val denominator = current_odds.split("/")[1].toInt()

        return numerator / denominator
    }

    fun getFormNum() : Int {
        val positions = formsummary.split("")
        var total = 0
        for (pos in positions){
            if(pos == "0"){
                total += 10
            }else if(pos == "-"){
                total += 5
            }else{
                try{
                    total += pos.toInt()
                }catch (e : NumberFormatException){
                    total += 5
                }
            }
        }
        return total
    }
}