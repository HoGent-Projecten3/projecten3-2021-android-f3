package com.example.faith

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.faith.databinding.FragmentHotelBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hotel.*

@AndroidEntryPoint
class HotelFragment : Fragment() {

    private lateinit var bottomNavigationView: BottomNavigationView

    /**
     * Method called upon starting view creation
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* Enabling data binding for fragments. slightly different because no immediate acces to root Activity */
        val binding = DataBindingUtil.inflate<FragmentHotelBinding>(
            inflater,
            R.layout.fragment_hotel,
            container,
            false
        )

        /* return */
        return binding.root
    }
    /**
     * Method called upon finishing view creation
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /* Enable correct routing for each hotel room */
        setRoomClickListeners()

        /* Rescale hotel images to the according screen size */
        scaleImages()
    }

    /**
     * Sets for each room image a clicklistener, following up with a routing to another fragment
     */
    private fun setRoomClickListeners() {
        val kamers: List<View> = listOf(
            image_cinema,
            image_bar,
            image_bibliotheek,
            image_infobalie,
            image_penthouse,
            image_trofeeKamer
        )
        for (kamer in kamers) {
            kamer.setOnClickListener { addRouting(kamer) }
        }
    }

    /**
     * Analyses the given room of the hotel and enables the corresponding fragment routing
     */

    private fun addRouting(kamer: View)
    {
      when (kamer.id)
      {
          R.id.image_bar -> Navigation.findNavController(kamer).navigate(R.id.action_hotelFragment_to_chatFragment)
          //Navigate to cinema room
          R.id.image_cinema -> Navigation.findNavController(kamer)
              .navigate(R.id.action_hotelFragment_to_cinemaFragment)
          // TODO
          R.id.image_infobalie -> Navigation.findNavController(kamer)
                  .navigate(R.id.action_hotelFragment_to_hulpbronListFragment)
          // TODO
          R.id.image_trofeeKamer -> Toast.makeText(getActivity(), "Trofee", Toast.LENGTH_SHORT)
              .show()
          // TODO
          R.id.image_penthouse -> Navigation.findNavController(kamer)
              .navigate(R.id.action_hotelFragment_to_penthouseFragment)
          // TODO
          R.id.image_bibliotheek -> Navigation.findNavController(kamer)
              .navigate(R.id.action_hotelFragment_to_mediumListFragment)
          else -> Toast.makeText(getActivity(), "Dit item is niet aanklikbaar.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun scaleImages() {
        val kamers: List<View> = listOf(
            image_cinema,
            image_bar,
            image_bibliotheek,
            image_infobalie,
            image_penthouse,
            image_trofeeKamer
        )

        // Obtain screen width & height DP
        val screenWidthDp = resources.configuration.screenWidthDp
        val screenHeightDp = resources.configuration.screenHeightDp

        // Define new size based on the screen DP. Height can be half the screen width, width has to then keep its ratio.
        val newHeight = (screenWidthDp * 0.5).toInt() // height half the screen width
        val newWidth = (screenWidthDp * 0.5).toInt()

        // Updating the dimensions for all rooms in pixel (Deprecated)
        /*
        image_penthouse.layoutParams.height = newWidth
        image_penthouse.layoutParams.width = newWidth
        */

        // Updating the dimensions for all rooms in DP
        val dimensionInDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            newHeight.toFloat(),
            resources.displayMetrics
        ).toInt() // new DP height

        // Only update height. these are constant. Width updated automatically, as wrap_content, and adjustviewbounds keeps ratio intact.
        for (kamer in kamers) {
            kamer.layoutParams.height = dimensionInDp
        }
        // image_penthouse.requestLayout()
    }
}
