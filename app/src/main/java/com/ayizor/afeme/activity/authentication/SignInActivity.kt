package com.ayizor.afeme.activity.authentication

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Pair.create
import android.view.View
import com.ayizor.afeme.R
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.white)));

        inits()
    }

    private fun inits() {
        dataService = Client.getClient(this)?.create(ApiInterface::class.java)
        binding.tvSignup.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            val numberFieldPair = create<View, String>(binding.tilNumber, "number_field")
            val passwordFieldPair = create<View, String>(binding.tilPassword, "password_field")
            val signup_txt = create<View, String>(binding.tvSignup, "swich_txt")
            val button = create<View, String>(binding.btnSigin, "btn")
            val logo_txt = create<View, String>(binding.logoName, "logo_text")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val options =
                    ActivityOptions.makeSceneTransitionAnimation(
                        this,
                        numberFieldPair,
                        passwordFieldPair,
                        button,
                        signup_txt,
                        logo_txt
                    )
                startActivity(i, options.toBundle())
            }
        }

        binding.btnSigin.setOnClickListener {
            loginUser(
                binding.tilNumber.editText?.text.toString(),
                binding.tilPassword.editText?.text.toString()
            )
        }
    }


    private fun loginUser(phoneNUmber: String, password: String) {
        showLoading(this@SignInActivity)
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
                        callMainActivity(this@SignInActivity)


                         dismissLoading()
                    }

                }


            }

            override fun onFailure(call: Call<MainResponse>, t: Throwable) {

            }

        })
    }


}