package com.ayizor.afeme.utils

import android.annotation.SuppressLint
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.ayizor.afeme.R
import com.ayizor.afeme.model.CustomLocation
import com.ayizor.afeme.model.ScreenSize
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


object Utils {

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Logger.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Logger.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Logger.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }

    @SuppressLint("HardwareIds")
    fun getDeviceID(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun screenSize(context: Application): ScreenSize {
        val displayMetrics = DisplayMetrics()
        val windowsManager = context.getSystemService(WINDOW_SERVICE) as WindowManager
        windowsManager.defaultDisplay.getMetrics(displayMetrics)
        val deviceWidth = displayMetrics.widthPixels
        val deviceHeight = displayMetrics.heightPixels
        return ScreenSize(deviceWidth, deviceHeight)
    }

    fun dialogDouble(context: Context?, title: String?, callback: DialogListener) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.view_dialog_double)
        dialog.setCanceledOnTouchOutside(true)
        val d_title = dialog.findViewById<TextView>(R.id.d_title)
        val d_confirm = dialog.findViewById<TextView>(R.id.d_confirm)
        val d_cancel = dialog.findViewById<TextView>(R.id.d_cancel)
        d_title.text = title
        d_confirm.setOnClickListener {
            dialog.dismiss()
            callback.onCallback(true)
        }
        d_cancel.setOnClickListener {
            dialog.dismiss()
            callback.onCallback(false)
        }
        dialog.show()
    }

    fun dialogSingle(context: Context?, title: String?, callback: DialogListener) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.view_dialog_single)
        dialog.setCanceledOnTouchOutside(true)
        val d_title = dialog.findViewById<TextView>(R.id.d_title)
        val d_confirm = dialog.findViewById<TextView>(R.id.d_confirm)
        d_title.text = title
        d_confirm.setOnClickListener {
            dialog.dismiss()
            callback.onCallback(true)
        }
        dialog.show()
    }

    fun validEditText(editText: EditText?, errorText: String): Boolean {
        val fullName: String = editText?.text.toString()
        return if (fullName.isEmpty()) {
            if (editText != null) {
                editText.error = errorText
            }
            false
        } else {
            if (editText != null) {
                editText.error = null
            }
            true
        }
    }

    fun getCoordinateName(context: Context, latitude: Double, longitude: Double): CustomLocation {
        val geocoder = Geocoder(context, Locale.getDefault());
        val addresses = geocoder.getFromLocation(latitude, longitude, 1);
        val address =
            addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        val city = addresses[0].locality
        val state = addresses[0].adminArea
        val country = addresses[0].countryName
        val postalCode = addresses[0].postalCode
        val knownName = addresses[0].featureName // Only if available else return NULL

        return CustomLocation(city, state, country, postalCode, knownName)

    }

    fun replaceWords(word: String, replace: String?, newWord: String): String? {
        return word.replace(replace!!, newWord)
    }

    fun formatUsd(number: String): String {
        val formattedNumber = replaceWords(number, ",", "")
        val formatter: NumberFormat = if (formattedNumber?.length == 4) {
            DecimalFormat("#.###")
        } else if (formattedNumber?.length == 5) {
            DecimalFormat("##.###")
        } else if (formattedNumber?.length == 6) {
            DecimalFormat("###.###")
        } else if (formattedNumber?.length == 7) {
            DecimalFormat("#.###.###")
        } else if (formattedNumber?.length == 8) {
            DecimalFormat("##.###.###")
        } else if (formattedNumber?.length == 9) {
            DecimalFormat("###.###.###")
        } else if (formattedNumber?.length == 10) {
            DecimalFormat("#.###.###.###")
        } else if (formattedNumber?.length == 11) {
            DecimalFormat("##.###.###.###")
        } else {
            DecimalFormat("#")
        }
        val maybeDouble = formattedNumber?.toDoubleOrNull()
        return if (maybeDouble != null) {
            formatter.format(formattedNumber.toDouble())
        } else {
            ""
        }

    }

}

interface DialogListener {
    fun onCallback(isChosen: Boolean)
}
