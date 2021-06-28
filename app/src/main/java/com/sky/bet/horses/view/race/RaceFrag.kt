package com.sky.bet.horses.view.race

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButtonToggleGroup
import com.sky.bet.horses.R
import com.sky.bet.horses.model.Race
import com.sky.bet.horses.view.webview.WebViewFrag
import com.sky.bet.horses.viewmodel.RacesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RaceFrag : Fragment() {

    private val viewModel: RacesViewModel by activityViewModels()
    private val adapter = RidesAdapter(arrayListOf()) { onOddsClick() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_race, container, false)

        activity?.title = getString(R.string.race)

        setupList(view)
        listenForData(view)
        setupSortToggles(view)

        return view
    }

    private fun setupSortToggles(view: View) {
        val toggles = view.findViewById<MaterialButtonToggleGroup>(R.id.toggle_group)
        toggles.addOnButtonCheckedListener{ group, checkedId, isChecked ->
            if(isChecked){
                adapter.sortList(checkedId)
            }
        }
    }

    fun onOddsClick() {
        val webViewFrag = WebViewFrag()
        webViewFrag.arguments = webViewFrag.createBundle(getString(R.string.skybet_horse_link))

        parentFragmentManager.commit {
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            replace(R.id.fragment_host, webViewFrag)
            addToBackStack(null)
        }

        activity?.title = getString(R.string.sky_bet_website)
    }

    private fun listenForData(view: View) {
        val toggles = view.findViewById<MaterialButtonToggleGroup>(R.id.toggle_group)
        viewModel.race.observe(viewLifecycleOwner) {
            adapter.submitNewList(it.rides)
            adapter.sortList(toggles.checkedButtonId)
            displayRaceData(view, it)
        }
    }

    private fun displayRaceData(view: View, race: Race) {
        val imgView = view.findViewById<ImageView>(R.id.race_img)
        val nameTxt = view.findViewById<TextView>(R.id.race_name_txt)
        val timeTxt = view.findViewById<TextView>(R.id.time_txt)
        val courseTxt = view.findViewById<TextView>(R.id.course_txt)
        val ageTxt = view.findViewById<TextView>(R.id.age_txt)
        val distanceTxt = view.findViewById<TextView>(R.id.distance_txt)
        val rideCountTxt = view.findViewById<TextView>(R.id.ride_count_txt)
        val raceStageTxt = view.findViewById<TextView>(R.id.stage_txt)
        val goingTxt = view.findViewById<TextView>(R.id.going_txt)
        val handicapTxt = view.findViewById<TextView>(R.id.handicap_txt)

        nameTxt.text = race.race_summary.name
        timeTxt.text = race.race_summary.getCountdown()
        imgView.setImageResource(race.image)
        courseTxt.text = race.race_summary.course_name
        ageTxt.text = race.race_summary.age
        distanceTxt.text = race.race_summary.distance
        rideCountTxt.text = race.race_summary.ride_count.toString()
        raceStageTxt.text = race.race_summary.race_stage
        goingTxt.text = race.race_summary.going
        handicapTxt.text =
            if (race.race_summary.has_handicap) getString(R.string.yes_caps)
            else getString(R.string.no_caps)
    }

    private fun setupList(view: View) {
        val recycler = view.findViewById<RecyclerView>(R.id.ride_recycler)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
    }

}