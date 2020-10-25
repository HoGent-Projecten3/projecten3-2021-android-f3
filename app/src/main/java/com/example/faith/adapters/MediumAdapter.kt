package com.example.faith.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.faith.data.Medium
import com.example.faith.databinding.ListItemMediumBinding

/**
 * adapter for recyclerview in plantlistfragment
 */
class MediumAdapter : ListAdapter<Medium, RecyclerView.ViewHolder>(MediumDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MediumViewHolder(
            ListItemMediumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class MediumViewHolder(
        private val binding : ListItemMediumBinding
    ): RecyclerView.ViewHolder(binding.root){
        init{
        binding.setClickListener {
            binding.medium?.let { medium ->
                navigateToMedium(medium, it)
            }
        }
    }


        private fun navigateToMedium(
            medium: Medium,
            view : View
        ){
        }
    }

}


private class MediumDiffCallback : DiffUtil.ItemCallback<Medium>(){

    override fun areItemsTheSame(oldItem: Medium, newItem: Medium): Boolean {
        return oldItem.mediumId == newItem.mediumId
    }

    override fun areContentsTheSame(oldItem: Medium, newItem: Medium): Boolean {
        return oldItem == newItem
    }
}