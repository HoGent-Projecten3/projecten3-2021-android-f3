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
import com.example.faith.databinding.DoelViewBinding
import com.example.faith.viewmodels.PenthouseViewModel


class DoelAdapter: ListAdapter<Doel, DoelAdapter.DoelViewHolder>(DoelDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoelViewHolder {
        return DoelViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DoelViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DoelViewHolder(val binding: DoelViewBinding): RecyclerView.ViewHolder(binding.root){

        var state: State = NormalState(this)

        interface State{
            fun doLongClick(item: Doel)
            fun doClick(item: Doel)
            fun doelEditButton(item: Doel)
            fun doelConfirmEditButton(item: Doel)
            fun doelDeleteButton(item: Doel)
            fun doelAddButton(item: Doel)
            fun doelAddConfirmButton(item: Doel)
            fun doelCheckboxChange(item: Doel, checked: Boolean)
        }

        class EditState: State{

            var _doelViewHolder: DoelViewHolder

            constructor(doelViewHolder: DoelViewHolder){
                _doelViewHolder = doelViewHolder
                doelViewHolder.binding.doelEditButton.visibility = View.VISIBLE
                doelViewHolder.binding.doelDeleteButton.visibility = View.VISIBLE
                doelViewHolder.binding.doelAddButton.visibility = View.VISIBLE
                doelViewHolder.binding.doelColapseText.visibility = View.GONE
                doelViewHolder.binding.doelListSec.visibility = View.VISIBLE
                doelViewHolder.binding.doelText.typeface = Typeface.DEFAULT
            }

            override fun doLongClick(item: Doel) {
                _doelViewHolder.state = NormalState(_doelViewHolder)
            }

            override fun doClick(item: Doel) {
                // nothing should happen here
            }

            override fun doelEditButton(item: Doel) {
                _doelViewHolder.binding.doelText.visibility = View.GONE
                _doelViewHolder.binding.doelEditButton.visibility = View.GONE
                _doelViewHolder.binding.doelDeleteButton.visibility = View.GONE
                _doelViewHolder.binding.doelAddButton.visibility = View.GONE
                _doelViewHolder.binding.doelEditText.setText(item.inhoud)
                _doelViewHolder.binding.doelEditText.visibility = View.VISIBLE
                _doelViewHolder.binding.doelConfirmEditButton.visibility = View.VISIBLE
            }

            override fun doelConfirmEditButton(item: Doel) {
                item.inhoud = _doelViewHolder.binding.doelEditText.text.toString()
                _doelViewHolder.binding.doelText.setText(item.inhoud)
                _doelViewHolder.binding.doelEditText.visibility = View.GONE
                _doelViewHolder.binding.doelConfirmEditButton.visibility = View.GONE
                _doelViewHolder.binding.doelText.visibility = View.VISIBLE
                _doelViewHolder.binding.doelEditButton.visibility = View.VISIBLE
                _doelViewHolder.binding.doelDeleteButton.visibility = View.VISIBLE
                _doelViewHolder.binding.doelAddButton.visibility = View.VISIBLE
                PenthouseViewModel.instance!!.syncDoelen()
            }

            override fun doelDeleteButton(item: Doel) {
                PenthouseViewModel.instance!!.verwijderDoel(item)
            }

            override fun doelAddButton(item: Doel) {
                _doelViewHolder.binding.doelAddButton.visibility = View.GONE
                _doelViewHolder.binding.doelEditButton.visibility = View.GONE
                _doelViewHolder.binding.doelDeleteButton.visibility = View.GONE
                _doelViewHolder.binding.doelAddEditText.visibility = View.VISIBLE
                _doelViewHolder.binding.doelAddConfirmButton.visibility = View.VISIBLE
            }

            override fun doelAddConfirmButton(item: Doel) {
                val naam = _doelViewHolder.binding.doelAddEditText.text.toString()
                val newDoel = Doel(naam,false,false, mutableListOf())
                item.addStap(newDoel)
                //adapter.notifyDataSetChanged()
                _doelViewHolder.binding.doelAddButton.visibility = View.VISIBLE
                _doelViewHolder.binding.doelEditButton.visibility = View.VISIBLE
                _doelViewHolder.binding.doelDeleteButton.visibility = View.VISIBLE
                _doelViewHolder.binding.doelAddEditText.visibility = View.GONE
                _doelViewHolder.binding.doelAddConfirmButton.visibility = View.GONE
                PenthouseViewModel.instance!!.syncDoelen()
            }

            override fun doelCheckboxChange(item: Doel, checked: Boolean) {
                item.checked = checked
                PenthouseViewModel.instance!!.syncDoelen()
            }

        }

        class ColapseState: State{

            var _doelViewHolder: DoelViewHolder

            constructor(doelViewHolder: DoelViewHolder){
                _doelViewHolder = doelViewHolder
                doelViewHolder.binding.doelListSec.visibility = View.GONE
                doelViewHolder.binding.doelText.typeface = Typeface.DEFAULT_BOLD
                doelViewHolder.binding.doelColapseText.visibility = View.VISIBLE
                doelViewHolder.binding.doelEditButton.visibility = View.GONE
                doelViewHolder.binding.doelDeleteButton.visibility = View.GONE
                doelViewHolder.binding.doelAddButton.visibility = View.GONE
            }

            override fun doLongClick(item: Doel) {
                _doelViewHolder.state = EditState(_doelViewHolder)
            }

            override fun doClick(item: Doel) {
                item.collapsed = false
                _doelViewHolder.state = NormalState(_doelViewHolder)
                PenthouseViewModel.instance!!.syncDoelen()
            }

            override fun doelEditButton(item: Doel) {
                Log.w("DoelAdapter.DoelViewHolder.ColapseState", "This was not supposed to happen")
            }

            override fun doelConfirmEditButton(item: Doel) {
                Log.w("DoelAdapter.DoelViewHolder.ColapseState", "This was not supposed to happen")
            }

            override fun doelDeleteButton(item: Doel) {
                Log.w("DoelAdapter.DoelViewHolder.ColapseState", "This was not supposed to happen")
            }

            override fun doelAddButton(item: Doel) {
                Log.w("DoelAdapter.DoelViewHolder.ColapseState", "This was not supposed to happen")
            }

            override fun doelAddConfirmButton(item: Doel) {
                Log.w("DoelAdapter.DoelViewHolder.ColapseState", "This was not supposed to happen")
            }

            override fun doelCheckboxChange(item: Doel, checked: Boolean) {
                item.checked = checked
                PenthouseViewModel.instance!!.syncDoelen()
            }
        }

        class NormalState: State{

            var _doelViewHolder: DoelViewHolder

            constructor(doelViewHolder: DoelViewHolder){
                _doelViewHolder = doelViewHolder
                doelViewHolder.binding.doelListSec.visibility = View.VISIBLE
                doelViewHolder.binding.doelText.typeface = Typeface.DEFAULT
                doelViewHolder.binding.doelColapseText.visibility = View.GONE
                doelViewHolder.binding.doelEditButton.visibility = View.GONE
                doelViewHolder.binding.doelDeleteButton.visibility = View.GONE
                doelViewHolder.binding.doelAddButton.visibility = View.GONE
            }

            override fun doLongClick(item: Doel) {
                _doelViewHolder.state = EditState(_doelViewHolder)
            }

            override fun doClick(item: Doel) {
                if(!item.stappen.isNullOrEmpty()) {
                    _doelViewHolder.binding.doelColapseText.text = "${item.stappen.size} substappen"
                    item.collapsed = true
                    _doelViewHolder.state = ColapseState(_doelViewHolder)
                    PenthouseViewModel.instance!!.syncDoelen()
                }
            }

            override fun doelEditButton(item: Doel) {
                Log.w("DoelAdapter.DoelViewHolder.NormalState", "This was not supposed to happen")
            }

            override fun doelConfirmEditButton(item: Doel) {
                Log.w("DoelAdapter.DoelViewHolder.NormalState", "This was not supposed to happen")
            }

            override fun doelDeleteButton(item: Doel) {
                Log.w("DoelAdapter.DoelViewHolder.NormalState", "This was not supposed to happen")
            }

            override fun doelAddButton(item: Doel) {
                Log.w("DoelAdapter.DoelViewHolder.NormalState", "This was not supposed to happen")
            }

            override fun doelAddConfirmButton(item: Doel) {
                Log.w("DoelAdapter.DoelViewHolder.NormalState", "This was not supposed to happen")
            }

            override fun doelCheckboxChange(item: Doel, checked: Boolean) {
                item.checked = checked
                PenthouseViewModel.instance!!.syncDoelen()
            }
        }

        fun bind(item: Doel) {

            //var viewModel = PenthouseViewModel.instance

            binding.doelText.text = item.inhoud
            val adapter = DoelAdapter()
            adapter.submitList(item.stappen)
            binding.doelListSec.adapter = adapter
            binding.doelCheckbox.isChecked = item.checked

            if(item.collapsed) {
                binding.doelColapseText.text = "${item.stappen.size} substappen"
                state = ColapseState(this)
            }

            binding.doelText.setOnLongClickListener( object: View.OnLongClickListener{
                override fun onLongClick(v: View?): Boolean {
                    state.doLongClick(item)
                    return true
                }
            })

            binding.doelText.setOnClickListener {
                state.doClick(item)
            }

            binding.doelEditButton.setOnClickListener{
                state.doelEditButton(item)
            }

            binding.doelConfirmEditButton.setOnClickListener {
                state.doelConfirmEditButton(item)
            }

            binding.doelDeleteButton.setOnClickListener {
                state.doelDeleteButton(item)
            }

            binding.doelAddButton.setOnClickListener {
                state.doelAddButton(item)
            }

            binding.doelAddConfirmButton.setOnClickListener {
                state.doelAddConfirmButton(item)
            }

            binding.doelCheckbox.setOnClickListener {
                state.doelCheckboxChange(item,binding.doelCheckbox.isChecked)
            }
        }

        companion object{
            fun from(parent: ViewGroup): DoelViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DoelViewBinding.inflate(layoutInflater, parent, false)
                return DoelViewHolder(binding)
            }
        }
    }

    class DoelDiffCallback : DiffUtil.ItemCallback<Doel>() {
        override fun areItemsTheSame(oldItem: Doel, newItem: Doel): Boolean {
            return oldItem.inhoud == newItem.inhoud
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Doel, newItem: Doel): Boolean {
            return oldItem == newItem
        }
    }

}
