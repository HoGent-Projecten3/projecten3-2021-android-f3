package com.example.faith

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation

import com.example.faith.databinding.FragmentHotelBinding
import kotlinx.android.synthetic.main.fragment_hotel.*


class HotelFragment : Fragment() {

    /**
     * Method called upon starting view creation
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View?
    {
        /* Enabling data binding for fragments. slightly different because no immediate acces to root Activity */
        val binding = DataBindingUtil.inflate<FragmentHotelBinding>(inflater, R.layout.fragment_hotel, container, false)


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
    }


    /**
     * Sets for each room image a clicklistener, following up with a routing to another fragment
     */
    private fun setRoomClickListeners() {
        val kamers : List <View> = listOf(image_cinema, image_bar, image_bibliotheek, image_infobalie, image_penthouse, image_trofeeKamer)
        for (kamer in kamers)
        {
            kamer.setOnClickListener{addRouting(kamer)}
        }
    }


    /**
     * Analyses the given room of the hotel and enables the corresponding fragment routing
     */
    private fun addRouting(kamer : View)
    {
      when (kamer.id)
      {
          // TODO
          R.id.image_bar -> Toast.makeText(getActivity(),"Bar",Toast.LENGTH_SHORT).show()
          //Navigate to cinema room
          R.id.image_cinema -> Navigation.findNavController(kamer).navigate(R.id.action_hotelFragment_to_cinemaFragment)
          // TODO
          R.id.image_infobalie -> Toast.makeText(getActivity(),"Infobalie",Toast.LENGTH_SHORT).show()
          // TODO
          R.id.image_trofeeKamer -> Toast.makeText(getActivity(),"Trofee",Toast.LENGTH_SHORT).show()
          // TODO
          R.id.image_penthouse -> Toast.makeText(getActivity(),"Penthouse",Toast.LENGTH_SHORT).show()
          // TODO
          R.id.image_bibliotheek -> Toast.makeText(getActivity(),"Bibliotheek",Toast.LENGTH_SHORT).show()
          else -> Toast.makeText(getActivity(),"Dit item is niet aanklikbaar.",Toast.LENGTH_SHORT).show()
        }
    }





}