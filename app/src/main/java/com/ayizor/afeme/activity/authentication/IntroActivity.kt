package com.ayizor.afeme.activity.authentication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ayizor.afeme.activity.MainActivity
import com.ayizor.afeme.databinding.ActivityIntroBinding
import com.ayizor.afeme.manager.PrefsManager

class IntroActivity : AppCompatActivity() {
    lateinit var binding: ActivityIntroBinding
    val TAG: String = IntroActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        if (PrefsManager(this).loadUserRegistered()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            inits()
        }


    }

    private fun inits() {


        binding.btnNext.setOnClickListener {
            if (checkCallPermissions()) {


                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    fun checkCallPermissions(): Boolean {
        val call =
            ContextCompat.checkSelfPermission(this@IntroActivity, Manifest.permission.CALL_PHONE)
        val listPermissionsNeeded = ArrayList<String>()
        if (call != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE)
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this@IntroActivity,
                listPermissionsNeeded.toTypedArray(),
                1
            )
            return false
        }
        return true
    }

}