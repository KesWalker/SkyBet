package com.sky.bet.horses.view.races

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.sky.bet.horses.R
import com.sky.bet.horses.model.Race
import com.squareup.picasso.Picasso

class RacesAdapter(
    private var races: MutableList<Race>,
    private var onRaceSelect: ((Race) -> Unit)
) :
    RecyclerView.Adapter<RacesAdapter.ViewHolder>() {

    // ideal solutions would use images from the data source.
    val raceImgs = intArrayOf(
        R.drawable.race_img_1,
        R.drawable.race_img_2,
        R.drawable.race_img_3,
        R.drawable.race_img_4,
        R.drawable.race_img_5,
        R.drawable.race_img_6,
        R.drawable.race_img_7,
        R.drawable.race_img_8,
        R.drawable.race_img_9
    )

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTxt: TextView
        val timeTxt: TextView
        val ageTxt: TextView
        val distanceTxt: TextView
        val goingTxt: TextView
        val raceImg: ImageView

        init {
            nameTxt = view.findViewById(R.id.race_name_txt)
            timeTxt = view.findViewById(R.id.time_txt)
            ageTxt = view.findViewById(R.id.age_txt)
            distanceTxt = view.findViewById(R.id.distance_txt)
            goingTxt = view.findViewById(R.id.going_txt)
            raceImg = view.findViewById(R.id.race_img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_race, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val raceSummary = races[position].race_summary

        holder.nameTxt.text = raceSummary.name
        holder.ageTxt.text = raceSummary.age
        holder.goingTxt.text = raceSummary.going
        holder.distanceTxt.text = raceSummary.distance
        holder.timeTxt.text = raceSummary.getCountdown()

        // load random horse racing image
        Picasso.get()
            .load(raceImgs[position % raceImgs.size])
            .into(holder.raceImg)

        holder.itemView.setOnClickListener {
            // save image for use in RaceFrag
            races[position].image = raceImgs[position % raceImgs.size]
            onRaceSelect.invoke(races[position])
        }
    }

    override fun getItemCount() = races.size

    fun submitNewList(races: MutableList<Race>) {
        this.races = races
        notifyDataSetChanged()
    }
}