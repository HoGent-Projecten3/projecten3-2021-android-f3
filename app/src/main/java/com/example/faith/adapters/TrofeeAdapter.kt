package com.example.faith.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.faith.TrofeekamerListFragmentDirections
import com.example.faith.data.Talent
import com.example.faith.databinding.ListItemTrofeeBinding
import com.example.faith.viewmodels.TrofeekamerListViewModel

/**
 * @author Arne De Schrijver
 */

class TrofeeAdapter : PagingDataAdapter<Talent, TrofeeAdapter.TrofeeViewHolder>(TrofeeDiffCallback()) {

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
                    talent ->
                    navigateToTrofee(talent, it)
                }
            }
        }

        private fun navigateToTrofee(
            talent: Talent,
            view: View
        ) {

            val direction : NavDirections

            if (talent.type == 2) {
                direction = TrofeekamerListFragmentDirections.actionTrofeekamerListFragmentToTalentDetailFragment(
                    talent.talentId
                )
            }
            else {
                direction = TrofeekamerListFragmentDirections.actionTrofeekamerListFragmentToPrestatieDetailFragment(
                    talent.talentId
                )
            }


            view.findNavController().navigate(direction)
        }
        fun bind(item: Talent) {
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

private class TrofeeDiffCallback : DiffUtil.ItemCallback<Talent>() {
    override fun areItemsTheSame(oldItem: Talent, newItem: Talent): Boolean {
        return oldItem.talentId == newItem.talentId
    }

    override fun areContentsTheSame(oldItem: Talent, newItem: Talent): Boolean {
        return oldItem == newItem
    }
}
