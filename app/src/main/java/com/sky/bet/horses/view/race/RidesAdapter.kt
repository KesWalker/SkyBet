package com.sky.bet.horses.view.race

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.sky.bet.horses.R
import com.sky.bet.horses.model.Ride

class RidesAdapter(
    private var rides: List<Ride>,
    private val oddsClickListener: (() -> Unit)
) :
    RecyclerView.Adapter<RidesAdapter.ViewHolder>() {

    val colours = intArrayOf(
        Color.GRAY,
        Color.RED,
        Color.YELLOW,
        Color.GREEN,
        Color.CYAN,
        Color.BLUE,
        Color.MAGENTA,
        Color.BLACK
    )
    var expandedPos = -1
    var prevExpandedPos = -1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTxt: TextView
        val clothNumTxt: TextView
        val daysSinceRunTxt: TextView
        val foaledTxt: TextView
        val sexTxt: TextView
        val formTxt: TextView
        val handicapTxt: TextView
        val oddsTxt: TextView
        val extraInfoGroup: Group
        val horseImg: ImageView

        init {
            nameTxt = view.findViewById(R.id.name_txt)
            clothNumTxt = view.findViewById(R.id.cloth_num_txt)
            daysSinceRunTxt = view.findViewById(R.id.days_run_txt)
            foaledTxt = view.findViewById(R.id.foaled_txt)
            sexTxt = view.findViewById(R.id.sex_txt)
            formTxt = view.findViewById(R.id.form_txt)
            handicapTxt = view.findViewById(R.id.handicap_txt)
            oddsTxt = view.findViewById(R.id.odds_txt)
            extraInfoGroup = view.findViewById(R.id.extra_info_group)
            horseImg = view.findViewById(R.id.horse_img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_horse, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ride = rides[position]
        val isExpanded = position == expandedPos


        // assign data to textviews
        holder.nameTxt.text = ride.horse.name
        holder.clothNumTxt.text = ride.cloth_number.toString()
        holder.daysSinceRunTxt.text = ride.horse.last_ran_days.toString()
        holder.formTxt.text =
            holder.itemView.context.getString(R.string.form_colon, ride.formsummary)
        holder.foaledTxt.text = ride.horse.foaled
        holder.handicapTxt.text = ride.handicap
        holder.oddsTxt.text = ride.current_odds
        holder.sexTxt.text =
            if (ride.horse.sex == "f") holder.itemView.context.getString(R.string.female)
            else holder.itemView.context.getString(R.string.male)
        holder.horseImg.imageTintList =
            ColorStateList.valueOf(colours[ride.cloth_number % colours.size])


        // display extra info if expanded
        if (isExpanded) {
            prevExpandedPos = position
        }
        holder.extraInfoGroup.visibility = if (isExpanded) View.VISIBLE else View.GONE


        // expand view on click
        holder.itemView.setOnClickListener {
            expandedPos = if (isExpanded) -1 else position
            notifyItemChanged(prevExpandedPos)
            notifyItemChanged(position)
        }
        holder.oddsTxt.setOnClickListener {
            oddsClickListener.invoke()
        }


        // create stripe list effect
        holder.itemView.setBackgroundResource(
            if (position % 2 == 0) R.drawable.white_ripple_box
            else R.drawable.primary_ripple_box
        )
    }

    override fun getItemCount() = rides.size

    fun submitNewList(rides: List<Ride>) {
        this.rides = rides
        notifyDataSetChanged()
    }

    fun sortList(sortBy: Int) {
        when (sortBy) {
            R.id.cloth_sort_toggle -> submitNewList(rides.sortedBy { it.cloth_number })
            R.id.form_sort_toggle -> submitNewList(rides.sortedBy { it.getFormNum() })
            R.id.odds_sort_toggle -> submitNewList(rides.sortedBy { it.getOddsNum() })
        }
    }

}