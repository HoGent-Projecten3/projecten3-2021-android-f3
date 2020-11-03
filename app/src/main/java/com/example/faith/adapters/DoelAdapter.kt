package com.example.faith.adapters


import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.faith.data.Doel
import com.example.faith.data.IDoel
import com.example.faith.data.Stap
import com.example.faith.databinding.DoelViewBinding
import com.example.faith.databinding.StapViewBinding


class DoelAdapter(val doelList: List<IDoel>): ListAdapter<IDoel, DoelAdapter.BaseViewHolder>(DoelDiffCallback()){

    override fun getItemViewType(position: Int): Int {
        when(getItem(position)){
            is Doel -> return 1
            is Stap -> return 0
            else -> throw Exception("Imposibru")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when(viewType){
            0 -> return StapViewHolder.from(parent)
            1 -> return DoelViewHolder.from(parent)
            else -> throw Exception("nooooo")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, this, doelList)
    }

    /*
    override fun getItemCount(): Int {
        return doel.size
    }*/

    class StapViewHolder(val binding: StapViewBinding): BaseViewHolder(binding.root){

        override fun bind(item: IDoel, parentAdapter: DoelAdapter, parentList: List<IDoel>) {
            binding.stapText.text = item.getNaam()
            binding.stapCheckBox.isChecked = item.isChecked()

            binding.stapCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                item.setChecked(isChecked)
            }

            binding.stapText.setOnLongClickListener( object: View.OnLongClickListener{
                override fun onLongClick(v: View?): Boolean {
                    if(binding.stapText.typeface == Typeface.DEFAULT){
                        binding.stapText.typeface = Typeface.DEFAULT_BOLD
                        binding.editButton.visibility = View.VISIBLE
                        binding.deleteButton.visibility = View.VISIBLE
                    }else{
                        binding.stapText.typeface = Typeface.DEFAULT
                        binding.editButton.visibility = View.GONE
                        binding.deleteButton.visibility = View.GONE
                    }
                    return true
                }
            })

            binding.editButton.setOnClickListener{
                binding.stapText.visibility = View.GONE
                binding.editButton.visibility = View.GONE
                binding.deleteButton.visibility = View.GONE
                binding.stapEditText.setText(item.getNaam())
                binding.stapEditText.visibility = View.VISIBLE
                binding.confirmEditButton.visibility = View.VISIBLE
            }

            binding.confirmEditButton.setOnClickListener {
                item.setNaam(binding.stapEditText.text.toString())
                binding.stapText.setText(item.getNaam())
                binding.stapEditText.visibility = View.GONE
                binding.confirmEditButton.visibility = View.GONE
                binding.stapText.visibility = View.VISIBLE
                binding.editButton.visibility = View.VISIBLE
                binding.deleteButton.visibility = View.VISIBLE
            }

            binding.deleteButton.setOnClickListener {
                /*val newList = parentAdapter.currentList.toMutableList()
                newList.remove(item)
                parentAdapter.submitList(newList)*/
                (parentList as MutableList).remove(item)
                parentAdapter.notifyDataSetChanged()
            }

        }

        companion object{
            fun from(parent: ViewGroup): BaseViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StapViewBinding.inflate(layoutInflater, parent, false)
                return StapViewHolder(binding)
            }
        }
    }

    class DoelViewHolder(val binding: DoelViewBinding): BaseViewHolder(binding.root){

        override fun bind(item: IDoel, parentAdapter: DoelAdapter, parentList: List<IDoel>) {
            binding.doelText.text = item.getNaam()
            val adapter = DoelAdapter(item.getStappen())
            adapter.submitList(item.getStappen())
            binding.doelListSec.adapter = adapter
            binding.doelText.setTypeface(Typeface.DEFAULT_BOLD)
            binding.doelCheckbox.isChecked = item.isChecked()

            if(item.isCollapsed()) {
                binding.doelListSec.visibility = View.GONE
                binding.doelText.typeface = Typeface.DEFAULT_BOLD
                binding.doelAddButton.visibility = View.GONE
            }

            binding.doelText.setOnLongClickListener( object: View.OnLongClickListener{
                override fun onLongClick(v: View?): Boolean {
                    if(binding.doelEditButton.visibility == View.VISIBLE){
                        binding.doelEditButton.visibility = View.GONE
                        binding.doelDeleteButton.visibility = View.GONE
                        binding.doelAddButton.visibility = View.GONE
                    }else{
                        binding.doelEditButton.visibility = View.VISIBLE
                        binding.doelDeleteButton.visibility = View.VISIBLE
                        binding.doelAddButton.visibility = View.VISIBLE
                    }
                    return true
                }
            })

            binding.doelText.setOnClickListener {
                if(binding.doelText.typeface == Typeface.DEFAULT_BOLD){
                    binding.doelListSec.visibility = View.VISIBLE
                    binding.doelText.typeface = Typeface.DEFAULT
                }else{
                    binding.doelListSec.visibility = View.GONE
                    binding.doelText.typeface = Typeface.DEFAULT_BOLD
                }
            }

            binding.doelEditButton.setOnClickListener{
                binding.doelText.visibility = View.GONE
                binding.doelEditButton.visibility = View.GONE
                binding.doelDeleteButton.visibility = View.GONE
                binding.doelEditText.setText(item.getNaam())
                binding.doelEditText.visibility = View.VISIBLE
                binding.doelConfirmEditButton.visibility = View.VISIBLE
            }

            binding.doelConfirmEditButton.setOnClickListener {
                item.setNaam(binding.doelEditText.text.toString())
                binding.doelText.setText(item.getNaam())
                binding.doelEditText.visibility = View.GONE
                binding.doelConfirmEditButton.visibility = View.GONE
                binding.doelText.visibility = View.VISIBLE
                binding.doelEditButton.visibility = View.VISIBLE
                binding.doelDeleteButton.visibility = View.VISIBLE
            }

            binding.doelDeleteButton.setOnClickListener {
                /*val newList = parentAdapter.currentList.toMutableList()
                newList.remove(item)
                parentAdapter.submitList(newList)*/
                (parentList as MutableList).remove(item)
                parentAdapter.notifyDataSetChanged()
            }

            binding.doelAddButton.setOnClickListener {
                binding.doelAddButton.visibility = View.GONE
                binding.doelAddEditText.visibility = View.VISIBLE
                binding.doelAddDoelButton.visibility = View.VISIBLE
                binding.doelAddStapButton.visibility = View.VISIBLE
            }

            binding.doelAddDoelButton.setOnClickListener {
                val naam = binding.doelAddEditText.text.toString()
                val newDoel = Doel(naam,false,false)
                /*item.addStap(newDoel)
                val newList = adapter.currentList.toMutableList()
                newList.add(newDoel)
                adapter.submitList(newList)
                adapter.submitList(item.getStappen())*/
                item.addStap(newDoel)
                adapter.notifyDataSetChanged()
                binding.doelAddButton.visibility = View.VISIBLE
                binding.doelAddEditText.visibility = View.GONE
                binding.doelAddDoelButton.visibility = View.GONE
                binding.doelAddStapButton.visibility = View.GONE
            }

            binding.doelAddStapButton.setOnClickListener {
                val naam = binding.doelAddEditText.text.toString()
                val newStap = Stap(naam,false)
                /*item.addStap(newStap)
                val newList = adapter.currentList.toMutableList()
                newList.add(newStap)
                adapter.submitList(newList)
                adapter.submitList(item.getStappen())*/
                item.addStap(newStap)
                adapter.notifyDataSetChanged()
                binding.doelAddButton.visibility = View.VISIBLE
                binding.doelAddEditText.visibility = View.GONE
                binding.doelAddDoelButton.visibility = View.GONE
                binding.doelAddStapButton.visibility = View.GONE
            }
        }

        companion object{
            fun from(parent: ViewGroup): BaseViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DoelViewBinding.inflate(layoutInflater, parent, false)
                return DoelViewHolder(binding)
            }
        }
    }

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: IDoel, parentAdapter: DoelAdapter, parentList: List<IDoel>)
    }

    class DoelDiffCallback : DiffUtil.ItemCallback<IDoel>() {
        override fun areItemsTheSame(oldItem: IDoel, newItem: IDoel): Boolean {
            Log.i("DoelAdapter", "itemCheck of ${oldItem.getNaam()}")
            return oldItem.isChecked() == newItem.isChecked()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: IDoel, newItem: IDoel): Boolean {
            return oldItem == newItem
        }
    }

}