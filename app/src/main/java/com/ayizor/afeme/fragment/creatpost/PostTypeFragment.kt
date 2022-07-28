package com.ayizor.afeme.fragment.creatpost

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.createpostadapters.SellTypesAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.FragmentPostTypeBinding
import com.ayizor.afeme.manager.PostPrefsManager
import com.ayizor.afeme.manager.PrefsManager
import com.ayizor.afeme.model.inmodels.SellType
import com.ayizor.afeme.model.response.SellResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostTypeFragment : Fragment(), SellTypesAdapter.OnSellTypeItemClickListener {

    lateinit var binding: FragmentPostTypeBinding
    var dataService: ApiInterface? = null
    var fragmentNumber = 1
    val TAG: String = PostTypeFragment::class.java.simpleName
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostTypeBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        activity?.findViewById<ProgressBar>(R.id.progress_bar_main_creat_post)?.progress =
            fragmentNumber
        dataService = Client.getClient(requireContext())?.create(ApiInterface::class.java)
        binding.rvSellType.layoutManager = GridLayoutManager(
            requireContext(), 1, GridLayoutManager.VERTICAL, false
        )
        getAllSellTypes()
    }

    private fun refreshSellTypesAdapter(filters: ArrayList<SellType>) {
        val adapter = SellTypesAdapter(requireContext(), filters, this)
        binding.rvSellType.adapter = adapter


    }

    private fun getAllSellTypes() {
        dataService!!.getAllSellTypes().enqueue(object : Callback<SellResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<SellResponse>,
                response: Response<SellResponse>
            ) {
                response.body()?.data?.let { refreshSellTypesAdapter(it) }
                binding.rvSellType.visibility = View.VISIBLE

            }

            override fun onFailure(call: Call<SellResponse>, t: Throwable) {
            }
        })

    }

    override fun onSellTypeItemClickListener(id: Int) {
        PostPrefsManager(requireContext()).storePostType(id)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
            .replace(R.id.fragment_container_creat_post, BuildningTypeFragment())
            .addToBackStack(PostTypeFragment::class.java.name).commit()

    }


}