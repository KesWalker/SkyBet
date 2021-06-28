package com.sky.bet.horses.view.races

import android.media.Image
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sky.bet.horses.R
import com.sky.bet.horses.model.Race
import com.sky.bet.horses.view.race.RaceFrag
import com.sky.bet.horses.viewmodel.RacesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RaceListFrag : Fragment() {

    // activity view models so that we can share the view model with RaceFrag.
    private val viewModel: RacesViewModel by activityViewModels()

    // create once in this fragments context so that we do not have to recreate each time
    // we select a new race list item.
    private val raceFrag = RaceFrag()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_race_list, container, false)

        activity?.title = getString(R.string.races)

        setupList(view)

        return view
    }

    private fun listenForData(view: View, adapter: RacesAdapter) {
        val spinner = view.findViewById<ProgressBar>(R.id.progress_spinner)
        val errTxt = view.findViewById<TextView>(R.id.sorry_txt)
        spinner.visibility = VISIBLE
        viewModel.races.observe(viewLifecycleOwner) {
            adapter?.submitNewList(it)
            spinner.visibility = GONE
        }
        viewModel.errorGettingAll.observe(viewLifecycleOwner){ error ->
            if(error){
                spinner.visibility = GONE
                errTxt.visibility = VISIBLE
            }else{
                errTxt.visibility = GONE
            }
        }
    }

    private fun setupList(view: View) {
        val adapter = RacesAdapter(arrayListOf()) { race: Race ->
            viewModel.selectRace(race)
            showRaceFrag()
        }
        val recycler = view.findViewById<RecyclerView>(R.id.race_recycler)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
        listenForData(view,adapter)
    }

    private fun showRaceFrag() {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(R.id.fragment_host, raceFrag)
            addToBackStack(null)
        }
    }
}