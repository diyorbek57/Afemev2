package com.ayizor.afeme.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.R
import com.ayizor.afeme.manager.UserPrefsManager
import com.ayizor.afeme.model.chat.inmodel.ChatResponseItem
import com.ayizor.afeme.utils.Logger
import com.ayizor.afeme.utils.Utils


class ItemChatAdapter(
    val context: Context,
    var chatlist: ArrayList<ChatResponseItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val TAG: String = ItemChatAdapter::class.java.simpleName
    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2

    // Inflates the appropriate layout according to the ViewType.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_me, parent, false)
            return SentMessageHolder(view)
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_other, parent, false)
            return ReceivedMessageHolder(view)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_other, parent, false)
            return ReceivedMessageHolder(view)
        }

    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val message: ChatResponseItem = chatlist[position]
        when (holder.itemViewType) {

            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder).bind(message)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageHolder).bind(message)
        }


    }

    // Determines the appropriate ViewType according to the sender of the message.
    override fun getItemViewType(position: Int): Int {
        val chat: ChatResponseItem = chatlist.get(position)
        val userID = UserPrefsManager(context).loadUserId().toString()
        Logger.e(TAG,"curretnuser id" + userID.toString())
        return if (chat.to == userID.toString()) {
            // If the current user is the sender of the message
            VIEW_TYPE_MESSAGE_SENT
        } else {
            // If some other user sent the message
            VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    override fun getItemCount(): Int {
        return chatlist.size
    }

    private class SentMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messageText: TextView
        var timeText: TextView
        fun bind(message: ChatResponseItem) {
            messageText.text = message.message

            // Format the stored timestamp into a readable String using method.
            timeText.text = Utils.formatingDateAsTime(message.created_at)
        }

        init {
            messageText = itemView.findViewById<View>(R.id.tv_message_me) as TextView
            timeText = itemView.findViewById<View>(R.id.tv_timestamp_me) as TextView
        }
    }

    private class ReceivedMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messageText: TextView
        var timeText: TextView
        fun bind(message: ChatResponseItem) {
            messageText.setText(message.message)

            // Format the stored timestamp into a readable String using method.
            timeText.setText(Utils.formatingDateAsTime(message.created_at))

            // Insert the profile image from the URL into the ImageView.
        }

        init {
            messageText = itemView.findViewById(R.id.tv_message_other)
            timeText = itemView.findViewById<View>(R.id.tv_timestamp_other) as TextView
        }
    }

}


