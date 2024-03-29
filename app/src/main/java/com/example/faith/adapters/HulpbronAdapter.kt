package com.example.faith.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.faith.HulpbronListFragmentDirections
import com.example.faith.data.Hulpbron
import com.example.faith.databinding.ListItemHulpbronBinding
import com.example.faith.viewmodels.HulpbronListViewModel
import kotlinx.android.synthetic.main.fragment_dagboek.*
import kotlinx.android.synthetic.main.fragment_infobalie.view.*
import retrofit2.Call
import retrofit2.Callback

class HulpbronAdapter : PagingDataAdapter<Hulpbron, HulpbronAdapter.HulpbronViewHolder>(
    HulpbronDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HulpbronViewHolder {
        return HulpbronViewHolder(
            ListItemHulpbronBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class HulpbronViewHolder(
        private val binding: ListItemHulpbronBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private fun navigateToHulpbron(
            hulpbron: Hulpbron,
            view: View
        ) {
            val direction =
                HulpbronListFragmentDirections.actionHulpbronListFragmentToHulpbronDetailFragment(
                    hulpbron.hulpbronId
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: Hulpbron) {
            binding.apply {
                hulpbron = item
                val viewModel = HulpbronListViewModel.instance
                if (binding.hulpbron?.auteurType.toString() == "Client")
                {
                    binding.hulpbronCardView.setOnLongClickListener {
                        if (binding.btVerwijder.visibility == View.GONE)
                            binding.btVerwijder.visibility = View.VISIBLE
                        else if (binding.btVerwijder.visibility == View.VISIBLE)
                            binding.btVerwijder.visibility = View.GONE
                        true
                    }
                }
                binding.setClickListener {
                    binding.hulpbron?.let { hulpbron ->
                        navigateToHulpbron(hulpbron, it)
                    }
                }
                binding.btVerwijder.setOnClickListener {
                    val call: Call<Int> =
                        viewModel!!.deleteHulpbron(binding.hulpbron!!.hulpbronId)
                    call.enqueue(
                        object : Callback<Int?> {
                            override fun onFailure(call: Call<Int?>, t: Throwable) {}
                            override fun onResponse(
                                call: Call<Int?>,
                                response: retrofit2.Response<Int?>
                            ) {
                                viewModel.deleteHulpbronRoom(binding.hulpbron!!.hulpbronId)
                            }
                        }
                    )
                    //this@HulpbronViewHolder.bindingAdapter
                    bindingAdapter?.notifyItemRemoved(this@HulpbronViewHolder.absoluteAdapterPosition)
                    bindingAdapter?.notifyDataSetChanged()
                }
                executePendingBindings()
            }
        }
    }

    override fun onBindViewHolder(holder: HulpbronViewHolder, position: Int) {
        val hulpbron = getItem(position)
        if (hulpbron != null) {
            holder.bind(hulpbron)
        }
    }
}

private class HulpbronDiffCallback : DiffUtil.ItemCallback<Hulpbron>() {
    override fun areItemsTheSame(oldItem: Hulpbron, newItem: Hulpbron): Boolean {
        return oldItem.hulpbronId == newItem.hulpbronId
    }

    override fun areContentsTheSame(oldItem: Hulpbron, newItem: Hulpbron): Boolean {
        return oldItem == newItem
    }
}
