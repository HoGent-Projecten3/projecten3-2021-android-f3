package com.example.faith.adapters

import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.faith.DagboekListFragmentDirections
import com.example.faith.R
import com.example.faith.data.Medium
import com.example.faith.databinding.ListItemDagboekBinding
import com.example.faith.viewmodels.DagboekDetailViewModel
import com.example.faith.viewmodels.DagboekListViewModel
import com.example.faith.viewmodels.HulpbronListViewModel
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback

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

            binding.dagboekCard.setOnLongClickListener {
                if (binding.btVerwijderDagboek.visibility == View.GONE)
                    binding.btVerwijderDagboek.visibility = View.VISIBLE
                else if (binding.btVerwijderDagboek.visibility == View.VISIBLE)
                    binding.btVerwijderDagboek.visibility = View.GONE
                true
            }
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





                val viewModel = DagboekListViewModel.instance
                binding.btVerwijderDagboek.setOnClickListener {
                    val call: Call<Medium> =
                        viewModel!!.removeMediumApi(binding.dagboek!!.mediumId)
                    call.enqueue(
                        object : retrofit2.Callback<Medium?> {
                            override fun onResponse(call: Call<Medium?>, response: retrofit2.Response<Medium?>) {
                                viewModel!!.deleteMediumRoom(binding.dagboek!!.mediumId)

                            }
                            override fun onFailure(call: Call<Medium?>, t: Throwable) {


                            }


                        }
                    )

                    this@DagboekViewHolder.bindingAdapter
                    bindingAdapter?.notifyItemRemoved(this@DagboekViewHolder.absoluteAdapterPosition) // Item wordt verwijderd maar aangezien er niet echt meteen iets weg is add hij het laatste item nog is?
                    bindingAdapter?.notifyDataSetChanged()
                }






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

private fun <T> Call<T>.enqueue(callback: Callback<Message?>) {
}

private class DagboekDiffCallback : DiffUtil.ItemCallback<Medium>() {

    override fun areItemsTheSame(oldItem: Medium, newItem: Medium): Boolean {
        return oldItem.mediumId == newItem.mediumId
    }

    override fun areContentsTheSame(oldItem: Medium, newItem: Medium): Boolean {
        return oldItem == newItem
    }
}
