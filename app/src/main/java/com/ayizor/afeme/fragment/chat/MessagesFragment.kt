package com.ayizor.afeme.fragment.chat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayizor.afeme.activity.ChatActivity
import com.ayizor.afeme.adapter.ItemMessagesAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.FragmentMessagesBinding
import com.ayizor.afeme.model.chat.Message
import com.ayizor.afeme.model.response.MessageResponse
import com.ayizor.afeme.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MessagesFragment : Fragment(), ItemMessagesAdapter.OnMessageClickListener {

    lateinit var binding: FragmentMessagesBinding
    val TAG: String = MessagesFragment::class.java.simpleName
    var dataService: ApiInterface? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessagesBinding.inflate(inflater, container, false)

        inits()
        return binding.root
    }

    private fun inits() {
        dataService = Client.getClient(requireContext())?.create(ApiInterface::class.java)

        binding.rvMessages.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        getMessages()
    }

    private fun refreshMessagesAdapter(messages: ArrayList<Message>) {
        val adapter =
            activity?.let { ItemMessagesAdapter(it.applicationContext, messages, this) }
        binding.rvMessages.adapter = adapter
//        binding.progressBar.visibility = View.GONE
//        binding.llMain.visibility = View.VISIBLE

    }

    private fun getMessages() {
        dataService?.getMessages()?.enqueue(object : Callback<MessageResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {

                    response.body()?.let { refreshMessagesAdapter(it) }


                }

            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
            }

        })

    }

    override fun onMessageClickListener(chatId: Int, userId: Int, name: String, lastname: String) {
        val intent = Intent(requireContext(), ChatActivity::class.java)
        Logger.e(TAG, chatId.toString())
        Logger.e(TAG, userId.toString())
        intent.putExtra("chatId", chatId)
        intent.putExtra("userId", userId)
        startActivity(intent)
    }


}