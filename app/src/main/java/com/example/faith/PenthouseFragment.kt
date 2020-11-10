package com.example.faith

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.faith.adapters.DoelAdapter
import com.example.faith.data.Doel
import com.example.faith.data.IDoel
import com.example.faith.data.Stap
import com.example.faith.databinding.FragmentPenthouseBinding
import com.example.faith.viewmodels.LoginViewModel
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

        viewModel.doelen.observe(this.viewLifecycleOwner, Observer{
            adapter.submitList(it)
        })

        viewModel.getDoelen()

        binding.mainAddButton.setOnClickListener {
            binding.mainAddConfirmButton.visibility = View.VISIBLE
            binding.mainAddEditText.visibility = View.VISIBLE
            viewModel.setDoelen(adapter.currentList)
            viewModel.syncDoelen()
        }

        binding.mainAddConfirmButton.setOnClickListener {
            val doel = Doel(binding.mainAddEditText.text.toString(), false, true)
            doel.addStap(Stap("Eerste stap",false))
            viewModel.addDoel(doel)
            adapter.notifyDataSetChanged()
            binding.mainAddConfirmButton.visibility = View.GONE
            binding.mainAddEditText.visibility = View.GONE
        }

        return binding.root
    }
}