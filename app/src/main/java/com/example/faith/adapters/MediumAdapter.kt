package com.example.faith.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.faith.MediumListFragmentDirections
import com.example.faith.data.Medium
import com.example.faith.databinding.ListItemMediumBinding
/**
 * @author Remi Mestdagh
 * adapter for recyclerview in mediumlistfrag
 */
class MediumAdapter : PagingDataAdapter<Medium, MediumAdapter.MediumViewHolder>(MediumDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediumViewHolder {
        return MediumViewHolder(
            ListItemMediumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    }

    class MediumViewHolder(
        private val binding: ListItemMediumBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.photo?.let { photo ->
                    navigateToMedium(photo, it)
                }
            }
        }

        private fun navigateToMedium(
            photo: Medium,
            view: View
        ) {
            val direction =
                MediumListFragmentDirections.actionMediumListFragmentToMediumDetailFragment(
                    photo.mediumId
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: Medium) {
            binding.apply {
                photo = item
                executePendingBindings()
            }
        }
    }

    override fun onBindViewHolder(holder: MediumViewHolder, position: Int) {
        val medium = getItem(position)
        if (medium != null) {
            holder.bind(medium)
        }
    }
}
private class MediumDiffCallback : DiffUtil.ItemCallback<Medium>() {

    override fun areItemsTheSame(oldItem: Medium, newItem: Medium): Boolean {
        return oldItem.mediumId == newItem.mediumId
    }

    override fun areContentsTheSame(oldItem: Medium, newItem: Medium): Boolean {
        return oldItem == newItem
    }
}
