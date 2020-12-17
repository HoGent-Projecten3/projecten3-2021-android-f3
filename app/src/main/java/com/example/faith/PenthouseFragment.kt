package com.example.faith

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.faith.adapters.DoelAdapter
import com.example.faith.data.Doel
import com.example.faith.databinding.FragmentPenthouseBinding
import com.example.faith.viewmodels.PenthouseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PenthouseFragment : Fragment() {

    private lateinit var palmIdleAnimation: AnimationDrawable
    private val viewModel: PenthouseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentPenthouseBinding.inflate(inflater, container, false)

        val adapter = DoelAdapter()
        binding.doelList.adapter = adapter

        binding.doelList.itemAnimator = null

        viewModel.doelen.observe(
            this.viewLifecycleOwner,
            Observer {
                adapter.submitList(it)
            }
        )

        viewModel.getDoelen()

        binding.mainAddButton.setOnClickListener {
            binding.mainAddConfirmButton.visibility = View.VISIBLE
            binding.mainAddEditText.visibility = View.VISIBLE
            binding.mainAddEditText.bringToFront()
        }

        binding.mainAddConfirmButton.setOnClickListener {
            val inhoud = binding.mainAddEditText.text.toString()
            if (inhoud.isNullOrEmpty()) {
                Toast.makeText(it.context, "Inhoud mag niet leeg zijn!", Toast.LENGTH_LONG).show()
            } else if (inhoud.length > 15) {
                Toast.makeText(it.context, "Inhoud mag niet langer dan 15 tekens zijn!", Toast.LENGTH_LONG).show()
            } else {
                val doel = Doel(inhoud, false, false, mutableListOf<Doel>())
                viewModel.addDoel(doel)
                viewModel.syncDoelen()
            }
            binding.mainAddConfirmButton.visibility = View.GONE
            binding.mainAddEditText.visibility = View.GONE
        }

        //Activeer de logo animatie
        binding.palmIcon.apply { setBackgroundResource(R.drawable.palm_idle)
        palmIdleAnimation = background as AnimationDrawable }
        palmIdleAnimation.start()

        return binding.root
    }
}
