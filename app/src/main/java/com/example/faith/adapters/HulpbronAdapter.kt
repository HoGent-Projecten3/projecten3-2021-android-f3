package com.example.faith.adapters

import android.graphics.Typeface
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.findNavController
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.faith.HulpbronListFragmentDirections
import com.example.faith.data.ApiHulpbron
import com.example.faith.data.Hulpbron
import com.example.faith.data.HulpbronRepository
import com.example.faith.databinding.ListItemHulpbronBinding
import com.example.faith.viewmodels.HulpbronDetailViewModel
import com.example.faith.viewmodels.HulpbronListViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_dagboek.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.await
import java.io.Console
import javax.inject.Inject


class HulpbronAdapter : PagingDataAdapter<ApiHulpbron, HulpbronAdapter.HulpbronViewHolder>(
        HulpbronDiffCallback()
){

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
    ): RecyclerView.ViewHolder(binding.root) {
        init {
                binding.hulpbronCardView.setOnLongClickListener {
                    if(binding.btVerwijder.visibility ==View.GONE)
                        binding.btVerwijder.visibility = View.VISIBLE
                    else if(binding.btVerwijder.visibility ==View.VISIBLE)
                        binding.btVerwijder.visibility = View.GONE
                    true
                }


            binding.setClickListener {
                binding.hulpbron?.let { hulpbron ->
                    navigateToHulpbron(hulpbron, it)
                }

            }
        }
        private fun navigateToHulpbron(
                hulpbron: ApiHulpbron,
                view: View
        ) {
            val direction = HulpbronListFragmentDirections.actionHulpbronListFragmentToHulpbronDetailFragment(
                    hulpbron.hulpbronId
            )
            view.findNavController().navigate(direction)
        }

        fun bind(item: ApiHulpbron){
            binding.apply {
                hulpbron = item
                val viewModel = HulpbronListViewModel.instance
                binding.btVerwijder.setOnClickListener {
                    val call: Call<Message> = viewModel!!.deleteHulpbron(binding.hulpbron!!.hulpbronId)
                    call.enqueue(
                        object : Callback<Message?> {
                            override fun onFailure(call: Call<Message?>, t: Throwable) {
                                Log.d("Failure",call.toString())
                            }

                            override fun onResponse(call: Call<Message?>, response: retrofit2.Response<Message?>) {
                                Log.d("Succes",call.toString())
                                //bindingAdapter?.notifyItemRemoved(binding.hulpbron!!.hulpbronId)
                            }
                        }
                    )
                }
                executePendingBindings()
            }
        }
    }




    override fun onBindViewHolder(holder: HulpbronViewHolder, position: Int) {
        val hulpbron = getItem(position)
        if(hulpbron!=null){
            holder.bind(hulpbron)
            }
        }
    }
    private class HulpbronDiffCallback : DiffUtil.ItemCallback<ApiHulpbron>(){
    override fun areItemsTheSame(oldItem: ApiHulpbron, newItem: ApiHulpbron): Boolean {
        return oldItem.hulpbronId == newItem.hulpbronId
    }

    override fun areContentsTheSame(oldItem: ApiHulpbron, newItem: ApiHulpbron): Boolean {
        return oldItem == newItem
    }

}
