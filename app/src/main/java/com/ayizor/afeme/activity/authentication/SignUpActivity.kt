package com.ayizor.afeme.activity.authentication

import android.content.Intent
import android.os.Bundle
import com.ayizor.afeme.activity.BaseActivity
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.ActivitySignUpBinding
import com.ayizor.afeme.model.response.MainResponse
import com.ayizor.afeme.utils.Logger
import com.ayizor.afeme.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {
    lateinit var binding: ActivitySignUpBinding
    val TAG: String = SignUpActivity::class.java.simpleName
    lateinit var user_password: String
    lateinit var user_first_name: String
    lateinit var user_last_name: String
    lateinit var user_phone_number: String
    var dataService: ApiInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
    }

    private fun inits() {
        val user_device_id: String = Utils.getDeviceID(this)
        Logger.e(TAG, user_device_id)
        dataService = Client.getClient(this)?.create(ApiInterface::class.java)


        binding.btnSigup.setOnClickListener {
            if (Utils.validEditText(
                    binding.tilNumber.editText,
                    "Please enter your Phone number"
                )
            ) {
                user_phone_number = binding.tilNumber.editText?.text.toString()
                user_first_name = binding.tilFirstName.editText?.text.toString()
                user_last_name = binding.tilLastName.editText?.text.toString()
                user_password = binding.tilPassword.editText?.text.toString()
                sendPhoneNumber(user_phone_number)

            }
        }
    }

    private fun sendPhoneNumber(userPhoneNumber: String) {
        dataService?.sendPhoneNumber(userPhoneNumber)
            ?.enqueue(object : Callback<MainResponse> {
                override fun onResponse(
                    call: Call<MainResponse>,
                    response: Response<MainResponse>
                ) {
                    if (response.body()?.status == true) {
                        callConfirmCodeActivity()
                    } else {
                        showTopSnackBar(
                            binding.mainLayoutSignup,
                            response.body()?.message.toString()
                        )
                    }

                }

                override fun onFailure(call: Call<MainResponse>, t: Throwable) {

                }

            })
    }

    fun callConfirmCodeActivity() {
        val i = Intent(this, CodeConfirmActivity::class.java)
        i.putExtra("user_phone_number", user_phone_number)
        i.putExtra("user_first_name", user_first_name)
        i.putExtra("user_last_name", user_last_name)
        i.putExtra("user_password", user_password)
        startActivity(i)
    }


}

