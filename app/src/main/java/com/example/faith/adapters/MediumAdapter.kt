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
import com.example.faith.viewmodels.MediumListViewModel
import retrofit2.Call

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

            binding.photoCard.setOnLongClickListener {
                if (binding.btVerwijderMedium.visibility == View.GONE)
                    binding.btVerwijderMedium.visibility = View.VISIBLE
                else if (binding.btVerwijderMedium.visibility == View.VISIBLE)
                    binding.btVerwijderMedium.visibility = View.GONE
                true
            }

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

                val viewModel = MediumListViewModel.instance
                binding.btVerwijderMedium.setOnClickListener {
                    val call: Call<Medium> =
                        viewModel!!.removeMediumApi(binding.photo!!.mediumId)
                    call.enqueue(
                        object : retrofit2.Callback<Medium?> {
                            override fun onResponse(call: Call<Medium?>, response: retrofit2.Response<Medium?>) {
                                viewModel.deleteMediumRoom(binding.photo!!.mediumId)
                            }
                            override fun onFailure(call: Call<Medium?>, t: Throwable) {
                            }
                        }
                    )

                    this@MediumViewHolder.bindingAdapter
                    bindingAdapter?.notifyItemRemoved(this@MediumViewHolder.absoluteAdapterPosition)
                    bindingAdapter?.notifyDataSetChanged()
                }

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
