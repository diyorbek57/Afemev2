package com.ayizor.afeme.activity.authentication

import android.content.Intent
import android.os.Bundle
import com.ayizor.afeme.activity.BaseActivity
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.ActivitySignInBinding
import com.ayizor.afeme.manager.PrefsManager
import com.ayizor.afeme.model.response.MainResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : BaseActivity() {


    lateinit var binding: ActivitySignInBinding
    val TAG: String = SignInActivity::class.java.simpleName
    var dataService: ApiInterface? = null
    lateinit var user_password: String
    lateinit var user_first_name: String
    lateinit var user_last_name: String
    lateinit var user_phone_number: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
    }

    private fun inits() {
        dataService = Client.getClient(this)?.create(ApiInterface::class.java)
        binding.tvSignup.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
        }

        binding.btnSigin.setOnClickListener {
            loginUser(
                binding.tilNumber.editText?.text.toString(),
                binding.tilPassword.editText?.text.toString()
            )
        }
    }


    private fun loginUser(phoneNUmber: String, password: String) {
        //showLoading(this@CodeConfirmActivity)
        dataService?.login(phoneNUmber, password)?.enqueue(object : Callback<MainResponse> {

            override fun onResponse(call: Call<MainResponse>, response: Response<MainResponse>) {
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        response.body()?.data?.let {
                            PrefsManager(this@SignInActivity).storeUserRegisteredToken(
                                it
                            )
                        }
                        PrefsManager(this@SignInActivity).storeUserRegistered(response.body()?.status!!)
                        callMainActivity(this@SignInActivity, response.body()?.data.toString())


                        // dismissLoading()
                    }

                }


            }

            override fun onFailure(call: Call<MainResponse>, t: Throwable) {

            }

        })
    }


}