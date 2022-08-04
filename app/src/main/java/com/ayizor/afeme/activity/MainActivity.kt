package com.ayizor.afeme.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
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
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val TAG: String = MainActivity::class.java.simpleName
    var dataService: ApiInterface? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataService = Client.getClient(this)?.create(ApiInterface::class.java)
        getCurrentUser()
        setupNavigation()

        sharedPreferences = getSharedPreferences("user_db", Context.MODE_PRIVATE)
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
                if (response.isSuccessful && response.body()?.status == true) {


                    val id = response.body()?.data?.user_id
                    Logger.e(TAG, id.toString())
                    var editor = sharedPreferences.edit()
                    editor.putInt("id", response.body()?.data?.user_id!!)
                    editor.apply()
                    editor.commit()

                    UserPrefsManager(this@MainActivity).storeUserId(id.toString())
                    PrefsManager(this@MainActivity).storeUserId(response.body()!!.data?.user_id.toString())
                    UserPrefsManager(this@MainActivity).storeUserFirstname(response.body()!!.data?.user_name)
                    UserPrefsManager(this@MainActivity).storeUserLastname(response.body()!!.data?.user_last_name)
                    setupPusher()

                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Logger.e(TAG, t.stackTraceToString())
            }
        })
    }


    private fun setupPusher() {
        val options = PusherOptions()
        options.setCluster("ap2");

        val pusher = Pusher("946e10830eb1a9df0760", options)

        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
                Logger.i(
                    TAG,
                    "State changed from ${change.previousState} to ${change.currentState}"
                )
            }

            override fun onError(
                message: String,
                code: String,
                e: Exception
            ) {
                Logger.i(
                    TAG,
                    "There was a problem connecting! code ($code), message ($message), exception($e)"
                )
            }
        }, ConnectionState.ALL)

        val channel = pusher.subscribe("Chat" + UserPrefsManager(this).loadUserId())
        channel.bind("my-event") { event ->
            Logger.i(TAG, "Received event with data: $event")
        }
    }


}