package com.ayizor.afeme.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.ChatAdapter
import com.ayizor.afeme.databinding.ActivityChatBinding
import com.ayizor.afeme.model.Chat
import com.ayizor.afeme.utils.Logger
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import okhttp3.internal.wait
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException


class ChatActivity : BaseActivity() {

        val TAG: String = ChatActivity::class.java.simpleName



    lateinit var binding: ActivityChatBinding





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inits(savedInstanceState)
    }

    private fun inits(savedInstanceState: Bundle?) {

    }

}