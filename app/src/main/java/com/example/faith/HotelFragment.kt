package com.example.faith

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
}
