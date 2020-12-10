package com.example.faith.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.faith.TrofeekamerListFragmentDirections
import com.example.faith.data.ApiTalent
import com.example.faith.databinding.ListItemTrofeeBinding
import com.example.faith.viewmodels.TrofeekamerListViewModel

/**
 * @author Arne De Schrijver
 */

class TrofeeAdapter : PagingDataAdapter<ApiTalent, TrofeeAdapter.TrofeeViewHolder>(TrofeeDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrofeeViewHolder {
        return TrofeeViewHolder(
            ListItemTrofeeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class TrofeeViewHolder(
        private val binding: ListItemTrofeeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.talent?.let {
                        talent -> navigateToTrofee(talent, it)
                }
            }
        }

        private fun navigateToTrofee(
            talent: ApiTalent,
            view: View
        ) {
            val direction = TrofeekamerListFragmentDirections.actionTrofeekamerListFragmentToTalentDetailFragment(
                talent.talentId
            )
            view.findNavController().navigate(direction)
        }
        fun bind(item: ApiTalent) {
            binding.apply {
                talent = item
                val viewModel = TrofeekamerListViewModel.instance
                executePendingBindings()
            }
        }
    }

    override fun onBindViewHolder(holder: TrofeeViewHolder, position: Int) {
        val talent = getItem(position)
        if (talent != null) {
            holder.bind(talent)
        }
    }
}

private class TrofeeDiffCallback : DiffUtil.ItemCallback<ApiTalent>() {
    override fun areItemsTheSame(oldItem: ApiTalent, newItem: ApiTalent): Boolean {
        return oldItem.talentId == newItem.talentId
    }

    override fun areContentsTheSame(oldItem: ApiTalent, newItem: ApiTalent): Boolean {
        return oldItem == newItem
    }
}