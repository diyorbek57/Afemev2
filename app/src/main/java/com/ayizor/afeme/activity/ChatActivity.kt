package com.ayizor.afeme.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayizor.afeme.adapter.ItemChatAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.ActivityChatBinding
import com.ayizor.afeme.manager.PrefsManager
import com.ayizor.afeme.manager.UserPrefsManager
import com.ayizor.afeme.model.chat.inmodel.ChatResponseItem
import com.ayizor.afeme.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChatActivity : BaseActivity() {

    val TAG: String = ChatActivity::class.java.simpleName
    lateinit var binding: ActivityChatBinding
    var dataService: ApiInterface? = null
    lateinit var chatId: String
    lateinit var userId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getEnteredDatas()
        inits(savedInstanceState)
    }


    private fun inits(savedInstanceState: Bundle?) {
        dataService = Client.getClient(this)?.create(ApiInterface::class.java)

        binding.recyclerGchat.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )


        getChat()
    }

    private fun refreshChatAdapter(chat: ArrayList<ChatResponseItem>) {
        val adapter = ItemChatAdapter(applicationContext, chat)
        binding.recyclerGchat.adapter = adapter
//        binding.progressBar.visibility = View.GONE
//        binding.llMain.visibility = View.VISIBLE

    }

    private fun getChat() {
        Logger.e(TAG, UserPrefsManager(context).loadUserId().toString())
        Logger.e(TAG, PrefsManager(context).loadUserId().toString())
        dataService?.getAllMessages(chatId)
            ?.enqueue(object : Callback<ArrayList<ChatResponseItem>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<ArrayList<ChatResponseItem>>,
                    response: Response<ArrayList<ChatResponseItem>>
                ) {
                    if (response.isSuccessful && response.code() == 200) {


                        response.body()?.let { refreshChatAdapter(it) }


                    }

                }

                override fun onFailure(call: Call<ArrayList<ChatResponseItem>>, t: Throwable) {
                }

            })

    }

    private fun getEnteredDatas() {
        chatId = intent.getIntExtra("chatId",0).toString()
        userId = intent.getStringExtra("userId").toString()


    }
}