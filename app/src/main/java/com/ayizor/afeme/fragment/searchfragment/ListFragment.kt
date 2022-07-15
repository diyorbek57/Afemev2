package com.ayizor.afeme.fragment.searchfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayizor.afeme.adapter.ItemMainPostsAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.FragmentListBinding
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.model.response.GetPostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment : Fragment(), ItemMainPostsAdapter.OnPostItemClickListener {

    lateinit var binding: FragmentListBinding
    val TAG: String = ListFragment::class.java.simpleName
    var dataService: ApiInterface? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)

        inits()
        return binding.root
    }

    private fun inits() {
        dataService = Client.getClient(requireContext())?.create(ApiInterface::class.java)

        binding.rvPosts.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        getPosts()
    }

    private fun refreshPostsAdapter(filters: ArrayList<GetPost>) {
        val adapter = ItemMainPostsAdapter(requireContext(), filters, this)
        binding.rvPosts.adapter = adapter

    }

    override fun onPostItemClickListener(id: Int, latitude: String, longitude: String) {

    }

    private fun getPosts() {
        dataService?.getAllPosts()?.enqueue(object : Callback<GetPostResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<GetPostResponse>,
                response: Response<GetPostResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {

                    response.body()?.data?.let { refreshPostsAdapter(it) }


                }

            }

            override fun onFailure(call: Call<GetPostResponse>, t: Throwable) {
            }

        })

    }

}