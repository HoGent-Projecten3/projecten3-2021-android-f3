package com.example.faith.adapters

import com.example.faith.R
import com.example.faith.data.Bericht
import com.example.faith.databinding.ItemMessageOntvangBinding
import com.example.faith.databinding.ItemMessageVertuurBinding
import com.xwray.groupie.databinding.BindableItem
import java.text.SimpleDateFormat

/**
 * @author Remi Mestdagh & Jef Seys
 */
class SendMessageItem(private val message: Bericht) : BindableItem<ItemMessageVertuurBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_message_vertuur
    }

    override fun bind(viewBinding: ItemMessageVertuurBinding, position: Int) {
        viewBinding.message = message
        viewBinding.date = SimpleDateFormat("HH:mm:ss").format(message.datum)
    }
}

class ReceiveMessageItem(private val message: Bericht) : BindableItem<ItemMessageOntvangBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_message_ontvang
    }

    override fun bind(viewBinding: ItemMessageOntvangBinding, position: Int) {
        viewBinding.message = message
        viewBinding.date = SimpleDateFormat("HH:mm:ss").format(message.datum)
    }
}
