package com.ayizor.afeme.fragment.favoritesfragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayizor.afeme.R
import com.ayizor.afeme.activity.DetailsActivity
import com.ayizor.afeme.adapter.ItemMainPostsAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.FragmentOffersBinding
import com.ayizor.afeme.databinding.ItemBottomSheetMoreBinding
import com.ayizor.afeme.fragment.FavoritesFragment
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.model.response.MainResponse
import com.ayizor.afeme.model.response.UserResponse
import com.ayizor.afeme.utils.Logger
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        binding.progressBar.visibility = View.GONE
        binding.llEmpty.visibility = View.GONE

    }

    private fun getOffers() {

        dataService?.getCurrentUser()?.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful && response.body()?.status == true) {

                    response.body()!!.data?.user_favorites?.let { refreshPostsAdapter(it) }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Logger.e(TAG, t.stackTraceToString())
            }
        })
    }

    override fun onPostItemClickListener(id: Int, latitude: String, longitude: String) {
        val intent = Intent(requireContext(), DetailsActivity::class.java)
        intent.putExtra("POST_ID", id)
        intent.putExtra("POST_LATITUDE", latitude)
        intent.putExtra("POST_LONGITUDE", longitude)
        startActivity(intent)
    }

    override fun onActionsButtonClickListener(id: Int) {
        showSettingsBottomsheet(id)
    }

    override fun onLikeButtonClickListener(id: Int, likeStatus: Boolean) {
        dataService?.likePost(id)?.enqueue(object : Callback<MainResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<MainResponse>,
                response: Response<MainResponse>
            ) {


            }

            override fun onFailure(call: Call<MainResponse>, t: Throwable) {
            }

        })
    }


    fun showSettingsBottomsheet(post_id: Int) {
        val sheetDialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        val bottomSheetBinding: ItemBottomSheetMoreBinding =
            ItemBottomSheetMoreBinding.inflate(layoutInflater)
        sheetDialog.setContentView(bottomSheetBinding.root)


        bottomSheetBinding.llBsNote.setOnClickListener {

        }
        bottomSheetBinding.llBsShare.setOnClickListener {

        }
        bottomSheetBinding.llBsReport.setOnClickListener {

        }
        bottomSheetBinding.llBsHidePost.setOnClickListener {

        }
        bottomSheetBinding.ivBsClose.setOnClickListener {
            sheetDialog.dismiss()
        }
        sheetDialog.show();
        sheetDialog.window?.attributes?.windowAnimations = R.style.DialogAnimaton;
    }

}