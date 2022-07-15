package com.ayizor.afeme.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar


open class BaseActivity : AppCompatActivity() {
    lateinit var context: Context
    var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
    }

    fun showLoading(activity: Activity?) {
        val builder: AlertDialog.Builder? = activity?.let { AlertDialog.Builder(it) }

        val inflater: LayoutInflater? = activity?.layoutInflater

//        if (inflater != null) {
//            builder?.setView(inflater.inflate(R.layout.item_pregressbar_dialog, null))
//            builder?.setCancelable(true)
//            dialog = builder?.create()
//            dialog?.show()
//        }
    }

    fun dismissLoading() {
        dialog?.dismiss()
    }

//    fun callMainActivity(context: Context) {
//        val intent = Intent(context, MainActivity::class.java)
//        startActivity(intent)
//        finish()
//    }
//
//    fun callOnBoardingActivity(context: Context) {
//        val intent = Intent(context, OnBoardingActivity::class.java)
//        startActivity(intent)
//        finish()
//    }

    fun showTopSnackBar(layoutView: View, text: String) {
        val snack = Snackbar.make(
            layoutView,
            text,
            Snackbar.LENGTH_LONG
        )
        val view: View = snack.view
        val params: CoordinatorLayout.LayoutParams =
            view.layoutParams as CoordinatorLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.setLayoutParams(params)
        snack.show()
    }

}