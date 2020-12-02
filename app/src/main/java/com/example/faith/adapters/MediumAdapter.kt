package com.example.faith.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.faith.MediumListFragmentDirections
import com.example.faith.data.ApiMediumResponse
import com.example.faith.databinding.ListItemMediumBinding
/**
 * @author Remi Mestdagh
 * adapter for recyclerview in plantlistfragment
 */
class MediumAdapter : PagingDataAdapter<ApiMediumResponse, MediumAdapter.MediumViewHolder>(MediumDiffCallback()) {
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
            photo: ApiMediumResponse,
            view: View
        ) {
            val direction =
                MediumListFragmentDirections.actionMediumListFragmentToMediumDetailFragment(
                    photo.mediumId
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: ApiMediumResponse) {
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
private class MediumDiffCallback : DiffUtil.ItemCallback<ApiMediumResponse>() {

    override fun areItemsTheSame(oldItem: ApiMediumResponse, newItem: ApiMediumResponse): Boolean {
        return oldItem.mediumId == newItem.mediumId
    }

    override fun areContentsTheSame(oldItem: ApiMediumResponse, newItem: ApiMediumResponse): Boolean {
        return oldItem == newItem
    }
}
