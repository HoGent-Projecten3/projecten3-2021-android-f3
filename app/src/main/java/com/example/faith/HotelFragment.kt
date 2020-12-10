package com.example.faith

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.faith.databinding.FragmentHotelBinding
import com.example.faith.viewmodels.DagboekListViewModel
import com.example.faith.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hotel.*

@AndroidEntryPoint
class HotelFragment : Fragment() {

    private val viewModel: LoginViewModel by activityViewModels()
    private lateinit var penthouseIdleAnimation: AnimationDrawable
    private lateinit var barIdleAnimation: AnimationDrawable
    private lateinit var trofeeIdleAnimation: AnimationDrawable
    private lateinit var bibIdleAnimation: AnimationDrawable
    private lateinit var cinemaIdleAnimation: AnimationDrawable
    private lateinit var baliIdleAnimation: AnimationDrawable
    private lateinit var entranceIdleAnimation: AnimationDrawable


    /**
     * Method called upon starting view creation
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /* Enabling data binding for fragments. slightly different because no immediate acces to root Activity */
        val binding = DataBindingUtil.inflate<FragmentHotelBinding>(  inflater,  R.layout.fragment_hotel, container,false )

        /* activeerAnimaties */
        activateAnimations(binding)

        /* return */
        return binding.root
    }

    /**
     * Method called upon finishing view creation
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()


        viewModel.loginSuccesvol.observe(viewLifecycleOwner, Observer { loginSuccesvol ->
            if (loginSuccesvol) {
            } else {

                navController.navigate(R.id.action_global_loginFragment)
            }
        })

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
            image_bib,
            image_bali,
            image_penthouse,
            image_trofee
        )
        for (kamer in kamers) {
            kamer.setOnClickListener { addRouting(kamer) }
        }
    }

    /**
     * Analyses the given room of the hotel and enables the corresponding fragment routing
     */

    private fun addRouting(kamer: View) {

        when (kamer.id) {
            R.id.image_bar -> Navigation.findNavController(kamer)
                .navigate(R.id.action_hotelFragment_to_chatFragment)
            R.id.image_bib -> Navigation.findNavController(kamer)
                .navigate(R.id.action_hotelFragment_to_dagboekListFragment2)
            R.id.image_trofee ->
                Navigation.findNavController(kamer)
                    .navigate(R.id.action_hotelFragment_to_trofeekamerListFragment)
            R.id.image_penthouse ->
                Navigation.findNavController(kamer)
                    .navigate(R.id.action_hotelFragment_to_penthouseFragment)
            R.id.image_cinema ->
                Navigation.findNavController(kamer)
                    .navigate(R.id.action_hotelFragment_to_mediumListFragment)
            R.id.image_bali -> Navigation.findNavController(kamer)
                .navigate(R.id.action_hotelFragment_to_hulpbronListFragment)
            else -> Toast.makeText(
                getActivity(),
                "Dit item is niet aanklikbaar.",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun scaleImages() {
        val kamers: List<View> = listOf(
            image_cinema,
            image_bar,
            image_bali,
            image_penthouse,
            image_trofee,
            image_roof,
            image_entrance,
            image_bib
        )

        // Obtain screen width & height DP
        val screenWidthDp = resources.configuration.screenWidthDp
        val screenHeightDp = resources.configuration.screenHeightDp

        // Define new size based on the screen DP. Height can be half the screen width, width has to then keep its ratio.
        val newHeight = (screenWidthDp * 0.37).toInt() // height half the screen width
        val newWidth = (screenWidthDp * 0.5).toInt()

        // Updating the dimensions for all rooms in pixel (Deprecated)
        /*
        image_penthouse.layoutParams.height = newWidth
        image_penthouse.layoutParams.width = newWidth
        */

        // Updating the dimensions for all rooms in DP
        val dimensionHeightInDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newHeight.toFloat(),resources.displayMetrics).toInt() // new DP height
        val dimensionWidthInDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newWidth.toFloat(),resources.displayMetrics).toInt() // new DP height

        // Only update height. these are constant. Width updated automatically, as wrap_content, and adjustviewbounds keeps ratio intact.
        for (kamer in kamers) {
            kamer.layoutParams.height = dimensionHeightInDp
            kamer.layoutParams.width = dimensionWidthInDp
        }
        image_bali.layoutParams.width = dimensionWidthInDp*2
        image_penthouse.layoutParams.width = dimensionWidthInDp*2
        image_entrance.layoutParams.width = dimensionWidthInDp*2
        image_roof.layoutParams.width = dimensionWidthInDp*2
        //updating the top and bottom part individually
        //val top = image_top
        //val bottom = image_bottom
        //top.layoutParams.width = image_penthouse.layoutParams.width
        //bottom.layoutParams.width = image_penthouse.layoutParams.width
        // image_penthouse.requestLayout()
    }


    /**
     * Activeert voor alle imageviews in de layout hun corresponderende animatie files
     */
    private fun activateAnimations(binding : FragmentHotelBinding)
    {
        //bali
        binding.imageBali.apply { setBackgroundResource(R.drawable.final_bali_idle)
        baliIdleAnimation = background as AnimationDrawable }
        baliIdleAnimation.start()
        //bar
        binding.imageBar.apply { setBackgroundResource(R.drawable.final_bar_idle)
        barIdleAnimation = background as AnimationDrawable }
        barIdleAnimation.start()
        //trofee
        binding.imageTrofee.apply { setBackgroundResource(R.drawable.final_trofee_idle)
        trofeeIdleAnimation = background as AnimationDrawable }
        trofeeIdleAnimation.start()
        //bib
        binding.imageBib.apply { setBackgroundResource(R.drawable.final_bib_idle)
        bibIdleAnimation = background as AnimationDrawable }
        bibIdleAnimation.start()
        //cinema
        binding.imageCinema.apply { setBackgroundResource(R.drawable.final_cinema_idle)
        cinemaIdleAnimation = background as AnimationDrawable }
        cinemaIdleAnimation.start()
        //penthouse
        binding.imagePenthouse.apply { setBackgroundResource(R.drawable.final_penthouse_idle)
        penthouseIdleAnimation = background as AnimationDrawable }
        penthouseIdleAnimation.start()
        //entrance
        binding.imageEntrance.apply { setBackgroundResource(R.drawable.final_entrance_idle)
        entranceIdleAnimation = background as AnimationDrawable }
        entranceIdleAnimation.start()
    }
    /**
     * Allows for a global insertion of the menu
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }



    /**
     * Handler for specific selections in the menu bar
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.aboutFragment -> {
                NavigationUI.onNavDestinationSelected(item, this.findNavController())
            }
            R.id.loginFragment -> {
                viewModel.logout()
                NavigationUI.onNavDestinationSelected(item, this.findNavController())

            }


            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }



}


