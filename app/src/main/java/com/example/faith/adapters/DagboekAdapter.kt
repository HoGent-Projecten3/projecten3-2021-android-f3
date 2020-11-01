package com.example.faith.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.faith.DagboekListFragmentDirections
import com.example.faith.data.ApiDagboek
import com.example.faith.databinding.ListItemDagboekBinding
/**
 * @author Remi Mestdagh
 */
class DagboekAdapter : PagingDataAdapter<ApiDagboek, DagboekAdapter.DagboekViewHolder>(DagboekDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DagboekAdapter.DagboekViewHolder {
        return DagboekAdapter.DagboekViewHolder(
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
                binding.dagboek?.let {
                    dagboek ->
                    navigateToDagboek(dagboek, it)
                }
            }
        }

        private fun navigateToDagboek(
            dagboek: ApiDagboek,
            view: View
        ) {
            val direction = DagboekListFragmentDirections.actionDagboekListFragment2ToDagboekDetailFragment(
                dagboek.mediumId
            )
            view.findNavController().navigate(direction)
        }
        fun bind(item: ApiDagboek) {
            binding.apply {
                dagboek = item
                executePendingBindings()
            }
        }
    }

    override fun onBindViewHolder(holder: DagboekViewHolder, position: Int) {
        val dagboek = getItem(position)
        if (dagboek != null) {
            holder.bind(dagboek)
        }
    }
}
private class DagboekDiffCallback : DiffUtil.ItemCallback<ApiDagboek>() {
    override fun areItemsTheSame(oldItem: ApiDagboek, newItem: ApiDagboek): Boolean {
        return oldItem.mediumId == newItem.mediumId
    }

    override fun areContentsTheSame(oldItem: ApiDagboek, newItem: ApiDagboek): Boolean {
        return oldItem == newItem
    }
}
