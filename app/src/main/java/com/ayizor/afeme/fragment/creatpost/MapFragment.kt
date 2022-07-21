package com.ayizor.afeme.fragment.creatpost

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.FragmentBuildningTypeBinding
import com.ayizor.afeme.databinding.FragmentMapBinding
import com.ayizor.afeme.fragment.creatpost.apartment.RoomsFragment
import com.ayizor.afeme.manager.PostPrefsManager
import com.sucho.placepicker.AddressData
import com.sucho.placepicker.Constants
import com.sucho.placepicker.PlacePicker
import java.util.*


class MapFragment : Fragment() {
    lateinit var user_latitude: String
    lateinit var user_longitude: String
    lateinit var user_address: String
    var geocoder: Geocoder? = null
    lateinit var addresses: List<Address>;
    lateinit var binding: FragmentMapBinding
    val TAG: String = MapFragment::class.java.simpleName
    var fragmentNumber = 3
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMapBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    fun inits() {
        val intent = PlacePicker.IntentBuilder().setLatLong(
            41.301140,
            69.262558
        )  // Initial Latitude and Longitude the Map will load into
            .showLatLong(false)  // Show Coordinates in the Activity
            .setMapZoom(12.0f)  // Map Zoom Level. Default: 14.0
            .setAddressRequired(true) // Set If return only Coordinates if cannot fetch Address for the coordinates. Default: True
            .hideMarkerShadow(true) // Hides the shadow under the map marker. Default: False
            .setFabColor(R.color.bright_blue)
            .setPlaceSearchBar(
                false,
                Constants.GOOGLE_API_KEY
            ) //Activate GooglePlace Search Bar. Default is false/not activated. SearchBar is a chargeable feature by Google
            .onlyCoordinates(true)  //Get only Coordinates from Place Picker
            .hideLocationButton(true)   //Hide Location Button (Default: false)
            .disableMarkerAnimation(true)   //Disable Marker Animation (Default: false)
            .build(requireActivity())
        startActivityForResult(intent, Constants.PLACE_PICKER_REQUEST)
    }

    @SuppressLint("SetTextI18n")
    fun getCoordinateName(latitude: Double, longitude: Double) {
        geocoder = Geocoder(requireContext(), Locale.getDefault());
        addresses = geocoder!!.getFromLocation(latitude, longitude, 1);
        val address =
            addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        val city = addresses[0].locality
        val state = addresses[0].adminArea
        val country = addresses[0].countryName
        val postalCode = addresses[0].postalCode
        val knownName =
            addresses[0].featureName // Only if available else return NULL
        user_latitude = latitude.toString()
        user_longitude = longitude.toString()
        user_address = "$state, $city"
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val addressData = data?.getParcelableExtra<AddressData>(Constants.ADDRESS_INTENT)
                if (addressData != null) {
                    getCoordinateName(addressData.latitude, addressData.longitude)
                    user_latitude = addressData.latitude.toString()
                    user_longitude = addressData.longitude.toString()
                    PostPrefsManager(requireContext()).storeLongitude(user_longitude)
                    PostPrefsManager(requireContext()).storeLatitude(user_latitude)
                    //PostPrefsManager(requireContext()).storePostType(id)

                    parentFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.enter_from_right,
                            R.anim.exit_to_left,
                            R.anim.enter_from_left,
                            R.anim.exit_to_right
                        )
                        .replace(R.id.fragment_container_creat_post, RoomsFragment())
                        .addToBackStack(MapFragment::class.java.name).commit()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}