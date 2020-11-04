package com.example.faith.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.content.Context
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.faith.MediumListFragmentDirections
import com.example.faith.R
import com.example.faith.data.*
import com.example.faith.databinding.ListItemMediumBinding
import kotlinx.android.synthetic.main.ander_bericht.view.*
import kotlinx.android.synthetic.main.eigen_bericht.view.*

private const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_OTHER_MESSAGE = 2

class BerichtAdapter (val context: Context?, val gebruikerRepository: GebruikerRepository): RecyclerView.Adapter<BerichtAdapter.MessageViewHolder>(), PagingDataAdapter<ApiBericht, BerichtAdapter.BerichtViewHolder>(MediumDiffCallback()) {
    private val messages: ArrayList<Bericht> = ArrayList()

    open class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(message: Bericht) {}
    }

    inner class MyMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtMyMessage
        private var timeText: TextView = view.txtMyMessageTime

        override fun bind(message: Bericht) {
            messageText.text = message.text
            timeText.text = message.datum.toString()
        }
    }

    inner class OtherMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtOtherMessage
        private var userText: TextView = view.txtOtherUser
        private var timeText: TextView = view.txtOtherMessageTime

        override fun bind(message: Bericht) {
            messageText.text = message.text
            userText.text = message.verstuurder?.voornaam
            timeText.text = message.datum.toString()
        }
    }

    fun addMessage(message: Bericht){
        messages.add(message)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages.get(position)

        return if(/*gebruikerRepository.getGebruiker().execute().body()*/Gebruiker("Jef", "Seys", "jef.seys.y0431@student.hogent.be") == message.verstuurder) {
            VIEW_TYPE_MY_MESSAGE
        }
        else {
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if(viewType == VIEW_TYPE_MY_MESSAGE) {
            MyMessageViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.eigen_bericht, parent, false)
            )
        } else {
            OtherMessageViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.ander_bericht, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages.get(position)

        holder?.bind(message)
    }
    class BerichtViewHolder(
        private val binding: ListItemMediumBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.photo?.let { photo ->
                    navigateToMedium(photo, it)
                }
            }
        }

        private fun navigateToMedium(
            photo: ApiPhoto,
            view: View
        ) {
            val direction =
                MediumListFragmentDirections.actionMediumListFragmentToMediumDetailFragment(
                    photo.mediumId
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: ApiPhoto) {
            binding.apply {
                photo = item
                executePendingBindings()
            }
        }
    }
}