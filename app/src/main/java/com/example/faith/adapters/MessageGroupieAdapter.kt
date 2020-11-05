package com.example.faith.adapters

import com.example.faith.R
import com.example.faith.data.ApiBericht
import com.example.faith.data.BerichtXML
import com.example.faith.databinding.ItemMessageOntvangBinding
import com.example.faith.databinding.ItemMessageVertuurBinding
import com.xwray.groupie.databinding.BindableItem
import org.threeten.bp.format.DateTimeFormatter

/**
 * @author Remi Mestdagh & Jef Seys
 */
class SendMessageItem(private val message: BerichtXML) : BindableItem<ItemMessageVertuurBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_message_vertuur
    }

    override fun bind(viewBinding: ItemMessageVertuurBinding, position: Int) {
        viewBinding.message = message
        viewBinding.date = message.datum.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
    }
}

class ReceiveMessageItem(private val message: BerichtXML) : BindableItem<ItemMessageOntvangBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_message_ontvang
    }

    override fun bind(viewBinding: ItemMessageOntvangBinding, position: Int) {
        viewBinding.message = message
        viewBinding.date = message.datum.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
    }
}