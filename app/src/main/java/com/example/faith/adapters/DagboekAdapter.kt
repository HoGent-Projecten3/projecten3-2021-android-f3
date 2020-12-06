package com.example.faith.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.faith.DagboekListFragmentDirections
import com.example.faith.data.Medium
import com.example.faith.databinding.ListItemDagboekBinding

/**
 * @author Remi Mestdagh
 * adapter for recyclerview in dagboeklistfrag
 */
class DagboekAdapter : PagingDataAdapter<Medium, DagboekAdapter.DagboekViewHolder>(
    DagboekDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DagboekViewHolder {
        return DagboekViewHolder(
            ListItemDagboekBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    }

    class DagboekViewHolder(
        private val binding: ListItemDagboekBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.dagboek?.let { photo ->
                    navigateToMedium(photo, it)
                }
            }
        }

        private fun navigateToMedium(
            photo: Medium,
            view: View
        ) {
            val direction =
                DagboekListFragmentDirections.actionDagboekListFragment2ToDagboekDetailFragment(
                    photo.mediumId
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: Medium) {
            binding.apply {
                dagboek = item
                executePendingBindings()
            }
        }
    }

    override fun onBindViewHolder(holder: DagboekViewHolder, position: Int) {
        val medium = getItem(position)
        if (medium != null) {
            holder.bind(medium)
        }
    }

}
private class DagboekDiffCallback : DiffUtil.ItemCallback<Medium>() {

    override fun areItemsTheSame(oldItem: Medium, newItem: Medium): Boolean {
        return oldItem.mediumId == newItem.mediumId
    }

    override fun areContentsTheSame(oldItem: Medium, newItem: Medium): Boolean {
        return oldItem == newItem
    }
}
