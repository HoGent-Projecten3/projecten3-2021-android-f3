package com.example.faith

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.faith.adapters.DoelAdapter
import com.example.faith.data.Doel
import com.example.faith.data.Stap
import com.example.faith.databinding.FragmentPenthouseBinding
import com.example.faith.viewmodels.PenthouseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PenthouseFragment : Fragment() {

    private val viewModel: PenthouseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentPenthouseBinding.inflate(inflater, container, false)

        val adapter = DoelAdapter()
        binding.doelList.adapter = adapter

        binding.doelList.itemAnimator = null

        viewModel.doelen.observe(this.viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.getDoelen()

        binding.mainAddButton.setOnClickListener {
            binding.mainAddConfirmButton.visibility = View.VISIBLE
            binding.mainAddEditText.visibility = View.VISIBLE
            binding.mainAddEditText.bringToFront()
            //viewModel.setDoelen(adapter.currentList)
            //viewModel.syncDoelen()
        }

        binding.mainAddConfirmButton.setOnClickListener {
            val doel = Doel(binding.mainAddEditText.text.toString(), false, true)
            doel.addStap(Stap("Eerste stap", false))
            viewModel.addDoel(doel)
            //adapter.notifyDataSetChanged()
            viewModel.syncDoelen()
            binding.mainAddConfirmButton.visibility = View.GONE
            binding.mainAddEditText.visibility = View.GONE
        }

        return binding.root
    }
}
