package com.example.faith

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.faith.databinding.FragmentCinemaBinding


class CinemaFragment : Fragment() {

    /**
     * Method called upon starting view creation
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View?
    {
        /* Enabling data binding for fragments. slightly different because no immediate acces to root Activity */
        val binding = DataBindingUtil.inflate<FragmentCinemaBinding>(inflater, R.layout.fragment_cinema, container, false)

        /* return */
        return binding.root
    }


    /**
     * Method called upon finishing view creation
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /* Enable correct routing for each hotel room */

    }




}