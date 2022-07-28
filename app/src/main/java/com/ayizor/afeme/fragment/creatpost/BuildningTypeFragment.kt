package com.ayizor.afeme.fragment.creatpost

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.createpostadapters.CreatePostBuildingTypeAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.FragmentBuildningTypeBinding
import com.ayizor.afeme.manager.PostPrefsManager
import com.ayizor.afeme.manager.PrefsManager
import com.ayizor.afeme.model.Category
import com.ayizor.afeme.model.response.CategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


class BuildningTypeFragment : Fragment(),
    CreatePostBuildingTypeAdapter.OnBuildingTypeItemClickListener {
    lateinit var binding: FragmentBuildningTypeBinding
    val TAG: String = BuildningTypeFragment::class.java.simpleName
    var dataService: ApiInterface? = null
    var fragmentNumber = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBuildningTypeBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        activity?.findViewById<ProgressBar>(R.id.progress_bar_main_creat_post)?.progress =
            fragmentNumber
        dataService = Client.getClient(requireContext())?.create(ApiInterface::class.java)
        binding.rvBuildingType.layoutManager = GridLayoutManager(
            requireContext(), 2, GridLayoutManager.VERTICAL, false
        )




        getAllCategory()
    }

    private fun refreshCategoryAdapter(filters: ArrayList<Category>) {
        val adapter = CreatePostBuildingTypeAdapter(requireContext(), filters, this)
        binding.rvBuildingType.adapter = adapter


    }

    private fun getAllCategory() {
        dataService!!.getAllCategory().enqueue(object : Callback<CategoryResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                response.body()?.data?.let { refreshCategoryAdapter(it) }
                binding.rvBuildingType.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {

            }
        })

    }

    override fun onBuildingTypeItemClickListener(id: Int) {
        PostPrefsManager(requireContext()).storeBuildingType(id)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
            .replace(R.id.fragment_container_creat_post, MapFragment())
            .addToBackStack(BuildningTypeFragment::class.java.name).commit()


    }




}