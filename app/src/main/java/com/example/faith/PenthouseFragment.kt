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

    var doelen: MutableList<IDoel>

    private val viewModel: PenthouseViewModel by viewModels()

    init {
        var doel: IDoel = Doel("Doel 1", false,true)
        doel.addStap(Stap("Een eerste stap", true))
        doel.addStap(Stap("Een tweede stap",false))
        var subDoel: Doel = Doel("Een subdoel", false,true)
        subDoel.addStap(Stap("Eerst stap subdoel",false))
        subDoel.addStap(Stap("Tweede stap subdoel",false))
        var subSubDoel: Doel = Doel("Een sub-subdoel", false,true)
        subSubDoel.addStap(Stap("Eerste sub-subdoel stap",false))
        subSubDoel.addStap(Stap("Tweede sub-subdoel stap",false))
        subDoel.addStap(subSubDoel)
        doel.addStap(subDoel)
        doel.addStap(Stap("Een laatste stap",false))

        var doel2: IDoel = Doel("Doel 2", false,true)
        doel2.addStap(Stap("Een eerste stap",false))
        doel2.addStap(Stap("Een tweede stap",false))
        var subDoel2: Doel = Doel("Een subdoel", false,true)
        subDoel2.addStap(Stap("Eerst stap subdoel",false))
        subDoel2.addStap(Stap("Tweede stap subdoel",false))
        subDoel2.addStap(Stap("Derde stap subdoel",false))
        doel2.addStap(subDoel2)
        doel2.addStap(Stap("Een laatste stap",false))

        doelen = mutableListOf(doel, doel2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPenthouseBinding.inflate(inflater, container, false)

        val adapter = DoelAdapter(doelen)
        binding.doelList.adapter = adapter

        viewModel.doelen.observe(this.viewLifecycleOwner, Observer{
            adapter.submitList(it)
        })

        viewModel.getGebruikers()

        binding.mainAddButton.setOnClickListener {
            binding.mainAddConfirmButton.visibility = View.VISIBLE
            binding.mainAddEditText.visibility = View.VISIBLE
        }

        binding.mainAddConfirmButton.setOnClickListener {
            val doel = Doel(binding.mainAddEditText.text.toString(), false, true)
            doel.addStap(Stap("Eerste stap",false))
            doelen.add(doel)
            adapter.notifyDataSetChanged()
            binding.mainAddConfirmButton.visibility = View.GONE
            binding.mainAddEditText.visibility = View.GONE
        }

        return binding.root
    }
}