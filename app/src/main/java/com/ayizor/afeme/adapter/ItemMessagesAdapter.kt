package com.ayizor.afeme.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.ItemChatsBinding
import com.ayizor.afeme.model.chat.Message
import com.bumptech.glide.Glide


class ItemMessagesAdapter(
    val context: Context,
    var postsList: ArrayList<Message>,
    private val onChatItemClickListener: OnMessageClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val TAG: String = ItemMessagesAdapter::class.java.simpleName
    private lateinit var binding: ItemChatsBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        binding = ItemChatsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ItemMessagesViewHolder(binding)


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        with(holder) {
            with(postsList[position]) {
                binding.tvUsername.text = user.name + " " + user.lastname
                Glide.with(context)
                    .load(user.image)
                    .placeholder(R.drawable.default_profile_image)
                    .error(R.drawable.default_profile_image)
                    .into(binding.ivUsersImage)
                binding.rlMain.setOnClickListener {
                    onChatItemClickListener.onMessageClickListener(
                        chat.id,
                        user.id,
                        user.name,
                        user.lastname
                    )
                }
            }

        }

    }


    override fun getItemCount(): Int {
        return postsList.size
    }


    inner class ItemMessagesViewHolder(val binding: ItemChatsBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnMessageClickListener {
        fun onMessageClickListener(chatId: Int, userId: Int, name: String, lastname: String)
    }
}


