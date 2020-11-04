package com.example.faith.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.paging.filter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.faith.HulpbronListFragmentDirections
import com.example.faith.data.ApiHulpbron
import com.example.faith.data.Hulpbron
import com.example.faith.data.HulpbronRepository
import com.example.faith.databinding.ListItemHulpbronBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


class HulpbronAdapter : PagingDataAdapter<ApiHulpbron, HulpbronAdapter.HulpbronViewHolder>(
        HulpbronDiffCallback()
){


    private var tempList = mutableListOf<ApiHulpbron>()
    private var init = true;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HulpbronAdapter.HulpbronViewHolder {
        return HulpbronAdapter.HulpbronViewHolder(
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
                executePendingBindings()
            }
        }
    }

    suspend fun filter(tekst: String) {
        if (init)
        {
            for (i in 0 until this.itemCount)
            {
                this.getItem(i)?.let { tempList.add(it) };
            }
            init = false
        }

        var temp = tempList.filter { a -> a.titel.toLowerCase().contains(tekst.toLowerCase()) }
        var turbotemp = PagingData.from(temp)
        this.submitData(turbotemp)
        notifyDataSetChanged()

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
