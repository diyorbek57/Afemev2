package com.ayizor.afeme.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ayizor.afeme.activity.SettingsActivity
import com.ayizor.afeme.activity.authentication.IntroActivity
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.FragmentProfileBinding
import com.ayizor.afeme.manager.PrefsManager
import com.ayizor.afeme.manager.UserPrefsManager
import com.ayizor.afeme.model.response.MainResponse
import com.ayizor.afeme.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    val TAG: String = ProfileFragment::class.java.simpleName
    var dataService: ApiInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        inits()

        return binding.root
    }

    private fun inits() {
        binding.tvFullname.text = UserPrefsManager(requireContext()).loadUserFirstname().toString() + " " + UserPrefsManager(
            requireContext()
        ).loadUserLastname().toString()
        Logger.e(TAG,UserPrefsManager(requireContext()).loadUserFirstname().toString() + " " + UserPrefsManager(
            requireContext()
        ).loadUserLastname().toString())
        dataService = Client.getClient(requireContext())?.create(ApiInterface::class.java)
        binding.llSetting.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }
        binding.tvLogout.setOnClickListener {

            logout()
        }
    }

    private fun logout() {
        dataService?.logout()?.enqueue(object : Callback<MainResponse> {
            override fun onResponse(call: Call<MainResponse>, response: Response<MainResponse>) {

                UserPrefsManager(requireContext()).clearUserDatas()
                PrefsManager(requireContext()).storeUserRegistered(false)
                PrefsManager(requireContext()).storeUserRegisteredToken("")
                val intent = Intent(requireContext(), IntroActivity::class.java)
                startActivity(intent)
                activity?.finish()

                // dismissLoading()


            }

            override fun onFailure(call: Call<MainResponse>, t: Throwable) {

            }

        })
    }

    private fun displayUserDatas() {


    }


}