package com.ayizor.afeme.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.ActivityMainBinding
import com.ayizor.afeme.manager.PrefsManager
import com.ayizor.afeme.manager.UserPrefsManager
import com.ayizor.afeme.model.response.UserResponse
import com.ayizor.afeme.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val TAG: String = MainActivity::class.java.simpleName
    var dataService: ApiInterface? = null
    lateinit var user_token: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataService = Client.getClient(this)?.create(ApiInterface::class.java)

        setupNavigation()
        inits()
    }

    private fun inits() {
        Logger.e(TAG, PrefsManager(this).loadUserRegisteredToken().toString())


    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.navHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavView.setupWithNavController(navController)
    }

    private fun getCurrentUser() {
        Logger.e(TAG, PrefsManager(this).loadUserRegisteredToken().toString())
        dataService?.getCurrentUser()?.enqueue(object : Callback<UserResponse> {

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        val user = response.body()!!.data

                    }
                }


            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {

            }

        })
    }


}