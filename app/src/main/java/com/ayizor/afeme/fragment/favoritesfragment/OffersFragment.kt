package com.ayizor.afeme.fragment.favoritesfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayizor.afeme.adapter.ItemMainPostsAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.FragmentOffersBinding
import com.ayizor.afeme.fragment.FavoritesFragment
import com.ayizor.afeme.manager.PrefsManager
import com.ayizor.afeme.model.post.GetPost


class OffersFragment : Fragment(), ItemMainPostsAdapter.OnPostItemClickListener,
    ItemMainPostsAdapter.OnActionsButtonClickListener,
    ItemMainPostsAdapter.OnLikeButtonClickListener {

    lateinit var binding: FragmentOffersBinding
    val TAG: String = FavoritesFragment::class.java.simpleName
    var dataService: ApiInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOffersBinding.inflate(inflater, container, false)

        inits()
        return binding.root
    }

    private fun inits() {
        dataService = Client.getClient(requireContext())?.create(ApiInterface::class.java)

        binding.rvOffers.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        getOffers()
    }

    private fun refreshPostsAdapter(filters: ArrayList<GetPost>) {
        val adapter =
            activity?.let { ItemMainPostsAdapter(it.applicationContext, filters, this, this, this) }
        binding.rvOffers.adapter = adapter

    }

    private fun getOffers() {
        binding.progressBar.visibility = View.GONE
//        dataService?.getAllPosts()?.enqueue(object : Callback<GetPostResponse> {
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onResponse(
//                call: Call<GetPostResponse>,
//                response: Response<GetPostResponse>
//            ) {
//                if (response.isSuccessful && response.code() == 200) {
//
////                    response.body()?.data?.let { refreshPostsAdapter(it) }
//
//
//                }
//
//            }
//
//            override fun onFailure(call: Call<GetPostResponse>, t: Throwable) {
//            }
//
//        })
    }

    override fun onPostItemClickListener(id: Int, latitude: String, longitude: String) {

    }

    override fun onActionsButtonClickListener(id: Int) {

    }

    override fun onLikeButtonClickListener(id: Int, likeStatus: Boolean) {

    }


}