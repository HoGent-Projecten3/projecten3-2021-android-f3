package com.example.faith.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.faith.MediumListFragmentDirections
import com.example.faith.data.ApiPhoto
import com.example.faith.databinding.ListItemMediumBinding

/**
 * adapter for recyclerview in plantlistfragment
 */
class MediumAdapter : PagingDataAdapter<ApiPhoto, MediumAdapter.MediumViewHolder>(MediumDiffCallback()) {
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
            photo: ApiPhoto,
            view: View
        ) {
            val direction =
                MediumListFragmentDirections.actionMediumListFragmentToMediumDetailFragment(
                    photo.mediumId
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: ApiPhoto) {
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
private class MediumDiffCallback : DiffUtil.ItemCallback<ApiPhoto>() {

    override fun areItemsTheSame(oldItem: ApiPhoto, newItem: ApiPhoto): Boolean {
        return oldItem.mediumId == newItem.mediumId
    }

    override fun areContentsTheSame(oldItem: ApiPhoto, newItem: ApiPhoto): Boolean {
        return oldItem == newItem
    }
}
