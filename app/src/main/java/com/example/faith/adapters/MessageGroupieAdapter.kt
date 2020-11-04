package com.example.faith.adapters

import com.example.faith.R
import com.example.faith.data.ApiBericht
import com.example.faith.databinding.ItemMessageOntvangBinding
import com.example.faith.databinding.ItemMessageVertuurBinding
import com.xwray.groupie.databinding.BindableItem

class SendMessageItem(private val message: ApiBericht) : BindableItem<ItemMessageVertuurBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_message_vertuur
    }

    override fun bind(viewBinding: ItemMessageVertuurBinding, position: Int) {
        viewBinding.message = message
    }
}

class ReceiveMessageItem(private val message: ApiBericht) : BindableItem<ItemMessageOntvangBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_message_ontvang
    }

    override fun bind(viewBinding: ItemMessageOntvangBinding, position: Int) {
        viewBinding.message = message
    }
}