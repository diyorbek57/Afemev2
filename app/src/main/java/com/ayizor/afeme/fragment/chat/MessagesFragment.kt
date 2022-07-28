package com.ayizor.afeme.fragment.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ayizor.afeme.adapter.ItemFavoritesViewPagerAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.FragmentMessagesBinding
import com.ayizor.afeme.fragment.FavoritesFragment
import com.ayizor.afeme.manager.PrefsManager
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.model.response.GetPostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MessagesFragment : Fragment() {

    lateinit var binding: FragmentMessagesBinding
    val TAG: String = MessagesFragment::class.java.simpleName
    lateinit var adapter: ItemFavoritesViewPagerAdapter
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
        getMessages()
    }

    private fun getMessages() {
        dataService?.getAllPosts()?.enqueue(object : Callback<GetPostResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<GetPostResponse>,
                response: Response<GetPostResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {

                    response.body()?.data?.let { refreshMessagesAdapter(it) }


                }

            }

            override fun onFailure(call: Call<GetPostResponse>, t: Throwable) {
            }

        })

    }

    private fun refreshMessagesAdapter(list: ArrayList<GetPost>) {

    }
}