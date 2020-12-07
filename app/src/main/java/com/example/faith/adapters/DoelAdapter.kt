package com.example.faith.adapters


import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            fun doLongClick(item: Doel, view: View)
            fun doClick(item: Doel, view: View)
            fun doelEditButton(item: Doel, view: View)
            fun doelConfirmEditButton(item: Doel, view: View)
            fun doelDeleteButton(item: Doel, view: View)
            fun doelAddButton(item: Doel, view: View)
            fun doelAddConfirmButton(item: Doel, view: View)
            fun doelCheckboxChange(item: Doel, checked: Boolean, view: View)
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

            override fun doLongClick(item: Doel, view: View) {
                _doelViewHolder.state = NormalState(_doelViewHolder)
            }

            override fun doClick(item: Doel, view: View) {
                // nothing should happen here
            }

            override fun doelEditButton(item: Doel, view: View) {
                _doelViewHolder.binding.doelText.visibility = View.GONE
                _doelViewHolder.binding.doelEditButton.visibility = View.GONE
                _doelViewHolder.binding.doelDeleteButton.visibility = View.GONE
                _doelViewHolder.binding.doelAddButton.visibility = View.GONE
                _doelViewHolder.binding.doelEditText.setText(item.inhoud)
                _doelViewHolder.binding.doelEditText.visibility = View.VISIBLE
                _doelViewHolder.binding.doelConfirmEditButton.visibility = View.VISIBLE
            }

            override fun doelConfirmEditButton(item: Doel, view: View) {
                val nieuweInhoud = _doelViewHolder.binding.doelEditText.text.toString()
                if(nieuweInhoud.isNullOrEmpty()){
                    Toast.makeText(view.context, "Inhoud mag niet leeg zijn!", Toast.LENGTH_LONG).show()
                }else if(nieuweInhoud.length > 15){
                    Toast.makeText(view.context, "Inhoud mag niet langer dan 15 tekens zijn!", Toast.LENGTH_LONG).show()
                }else {
                    val newItem = PenthouseViewModel.instance!!.getDoel(item.inhoud)
                    if(newItem != null) newItem.inhoud = nieuweInhoud
                    _doelViewHolder.binding.doelText.setText(item.inhoud)
                    PenthouseViewModel.instance!!.syncDoelen()
                }
                _doelViewHolder.binding.doelEditText.visibility = View.GONE
                _doelViewHolder.binding.doelConfirmEditButton.visibility = View.GONE
                _doelViewHolder.binding.doelText.visibility = View.VISIBLE
                _doelViewHolder.binding.doelEditButton.visibility = View.VISIBLE
                _doelViewHolder.binding.doelDeleteButton.visibility = View.VISIBLE
                _doelViewHolder.binding.doelAddButton.visibility = View.VISIBLE
            }

            override fun doelDeleteButton(item: Doel, view: View) {
                PenthouseViewModel.instance!!.verwijderDoel(item)
            }

            override fun doelAddButton(item: Doel, view: View) {
                _doelViewHolder.binding.doelAddButton.visibility = View.GONE
                _doelViewHolder.binding.doelEditButton.visibility = View.GONE
                _doelViewHolder.binding.doelDeleteButton.visibility = View.GONE
                _doelViewHolder.binding.doelAddEditText.visibility = View.VISIBLE
                _doelViewHolder.binding.doelAddConfirmButton.visibility = View.VISIBLE
            }

            override fun doelAddConfirmButton(item: Doel, view: View) {
                val inhoud = _doelViewHolder.binding.doelAddEditText.text.toString()
                if(inhoud.isNullOrEmpty()){
                    Toast.makeText(view.context, "Inhoud mag niet leeg zijn!", Toast.LENGTH_LONG).show()
                }else if(inhoud.length > 15){
                    Toast.makeText(view.context, "Inhoud mag niet langer dan 15 tekens zijn!", Toast.LENGTH_LONG).show()
                }else{
                    val newDoel = Doel(inhoud,false,false, mutableListOf<Doel>())
                    val newItem = PenthouseViewModel.instance!!.getDoel(item.inhoud)
                    if(newItem != null) newItem.addStap(newDoel)
                    PenthouseViewModel.instance!!.syncDoelen()
                }
                _doelViewHolder.binding.doelAddButton.visibility = View.VISIBLE
                _doelViewHolder.binding.doelEditButton.visibility = View.VISIBLE
                _doelViewHolder.binding.doelDeleteButton.visibility = View.VISIBLE
                _doelViewHolder.binding.doelAddEditText.visibility = View.GONE
                _doelViewHolder.binding.doelAddConfirmButton.visibility = View.GONE
            }

            override fun doelCheckboxChange(item: Doel, checked: Boolean, view: View) {
                val newItem = PenthouseViewModel.instance!!.getDoel(item.inhoud)
                if(newItem != null) newItem.checked = checked
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

            override fun doLongClick(item: Doel, view: View) {
                _doelViewHolder.state = EditState(_doelViewHolder)
            }

            override fun doClick(item: Doel, view: View) {
                val newItem = PenthouseViewModel.instance!!.getDoel(item.inhoud)
                if(newItem != null) newItem.collapsed = false
                _doelViewHolder.state = NormalState(_doelViewHolder)
                PenthouseViewModel.instance!!.syncDoelen()
            }

            override fun doelEditButton(item: Doel, view: View) {
                Log.w("DoelAdapter.DoelViewHolder.ColapseState", "This was not supposed to happen")
                Toast.makeText(view.context, "This was not supposed to happen", Toast.LENGTH_LONG).show()
            }

            override fun doelConfirmEditButton(item: Doel, view: View) {
                Log.w("DoelAdapter.DoelViewHolder.ColapseState", "This was not supposed to happen")
                Toast.makeText(view.context, "This was not supposed to happen", Toast.LENGTH_LONG).show()
            }

            override fun doelDeleteButton(item: Doel, view: View) {
                Log.w("DoelAdapter.DoelViewHolder.ColapseState", "This was not supposed to happen")
                Toast.makeText(view.context, "This was not supposed to happen", Toast.LENGTH_LONG).show()
            }

            override fun doelAddButton(item: Doel, view: View) {
                Log.w("DoelAdapter.DoelViewHolder.ColapseState", "This was not supposed to happen")
                Toast.makeText(view.context, "This was not supposed to happen", Toast.LENGTH_LONG).show()
            }

            override fun doelAddConfirmButton(item: Doel, view: View) {
                Log.w("DoelAdapter.DoelViewHolder.ColapseState", "This was not supposed to happen")
                Toast.makeText(view.context, "This was not supposed to happen", Toast.LENGTH_LONG).show()
            }

            override fun doelCheckboxChange(item: Doel, checked: Boolean, view: View) {
                val newItem = PenthouseViewModel.instance!!.getDoel(item.inhoud)
                if(newItem != null) newItem.checked = checked
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

            override fun doLongClick(item: Doel, view: View) {
                _doelViewHolder.state = EditState(_doelViewHolder)
            }

            override fun doClick(item: Doel, view: View) {
                if(!item.stappen.isNullOrEmpty()) {
                    _doelViewHolder.binding.doelColapseText.text = "${item.aantalStappen} substappen"
                    val newItem = PenthouseViewModel.instance!!.getDoel(item.inhoud)
                    if(newItem != null) newItem.collapsed = true
                    _doelViewHolder.state = ColapseState(_doelViewHolder)
                    PenthouseViewModel.instance!!.syncDoelen()
                }
            }

            override fun doelEditButton(item: Doel, view: View) {
                Log.w("DoelAdapter.DoelViewHolder.NormalState", "This was not supposed to happen")
                Toast.makeText(view.context, "This was not supposed to happen", Toast.LENGTH_LONG).show()
            }

            override fun doelConfirmEditButton(item: Doel, view: View) {
                Log.w("DoelAdapter.DoelViewHolder.NormalState", "This was not supposed to happen")
                Toast.makeText(view.context, "This was not supposed to happen", Toast.LENGTH_LONG).show()
            }

            override fun doelDeleteButton(item: Doel, view: View) {
                Log.w("DoelAdapter.DoelViewHolder.NormalState", "This was not supposed to happen")
                Toast.makeText(view.context, "This was not supposed to happen", Toast.LENGTH_LONG).show()
            }

            override fun doelAddButton(item: Doel, view: View) {
                Log.w("DoelAdapter.DoelViewHolder.NormalState", "This was not supposed to happen")
                Toast.makeText(view.context, "This was not supposed to happen", Toast.LENGTH_LONG).show()
            }

            override fun doelAddConfirmButton(item: Doel, view: View) {
                Log.w("DoelAdapter.DoelViewHolder.NormalState", "This was not supposed to happen")
                Toast.makeText(view.context, "This was not supposed to happen", Toast.LENGTH_LONG).show()
            }

            override fun doelCheckboxChange(item: Doel, checked: Boolean, view: View) {
                val newItem = PenthouseViewModel.instance!!.getDoel(item.inhoud)
                if(newItem != null) newItem.checked = checked
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
                binding.doelColapseText.text = "${item.aantalStappen} substappen"
                state = ColapseState(this)
            }

            binding.doelText.setOnLongClickListener( object: View.OnLongClickListener{
                override fun onLongClick(v: View?): Boolean {
                    state.doLongClick(item, v!!)
                    return true
                }
            })

            binding.doelText.setOnClickListener {
                state.doClick(item, it)
            }

            binding.doelEditButton.setOnClickListener{
                state.doelEditButton(item, it)
            }

            binding.doelConfirmEditButton.setOnClickListener {
                state.doelConfirmEditButton(item, it)
            }

            binding.doelDeleteButton.setOnClickListener {
                state.doelDeleteButton(item, it)
                adapter.notifyDataSetChanged()
            }

            binding.doelAddButton.setOnClickListener {
                state.doelAddButton(item, it)
            }

            binding.doelAddConfirmButton.setOnClickListener {
                state.doelAddConfirmButton(item, it)
            }

            binding.doelCheckbox.setOnClickListener {
                state.doelCheckboxChange(item,binding.doelCheckbox.isChecked, it)
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
            return oldItem === newItem
        }
    }

}
