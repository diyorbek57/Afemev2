package com.ayizor.afeme.fragment.searchfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.model.CustomClusterItem
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.model.response.GetPostResponse
import com.ayizor.afeme.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapFragment : Fragment(), OnMapReadyCallback {
    //binding
    lateinit var binding: FragmentMapBinding

    //tag for this fragment
    val TAG: String = MapFragment::class.java.simpleName

    //service for call main api
    var dataService: ApiInterface? = null

    //arraylist for posts
    var postsList: ArrayList<GetPost> = ArrayList()

    //
    private lateinit var clusterManager: ClusterManager<CustomClusterItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMapBinding.inflate(inflater, container, false)
        inits(savedInstanceState)
        return binding.root
    }

    private fun inits(savedInstanceState: Bundle?) {

        dataService = Client.getClient(requireContext())?.create(ApiInterface::class.java)
        //setup map settings
        binding.mapViewSearch.onCreate(savedInstanceState)
        binding.mapViewSearch.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        //setup map settings
        googleMap.uiSettings.isZoomGesturesEnabled = true;
        googleMap.uiSettings.isScrollGesturesEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = false;
        //starter point of map
        val cameraPosition =
            CameraPosition.Builder().target(LatLng(41.303322, 69.256782)).zoom(6f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        //initialize cluster
        clusterManager = ClusterManager(context, googleMap)

        addPostsAsMarkerToMap(googleMap, clusterManager)
    }

    private fun addPostsAsMarkerToMap(
        googleMap: GoogleMap,
        clusterManager: ClusterManager<CustomClusterItem>
    ) {
        dataService?.getAllPosts()?.enqueue(object : Callback<GetPostResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<GetPostResponse>,
                response: Response<GetPostResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    //
                    postsList = response.body()?.data!!

                    for (i in 0 until postsList.size) {
                        val latlang = LatLng(
                            postsList[i].post_latitude!!.toDouble(),
                            postsList[i].post_longitude!!.toDouble()
                        )

                        val offsetItem = CustomClusterItem(
                            latlang.latitude,
                            latlang.longitude,
                            "Title $i",
                            "Snippet $i",
                            postsList[i]
                        )
                        clusterManager.addItem(offsetItem)

                        clusterManager.setOnClusterItemClickListener(mClusterItemClickListener);
                        googleMap.setOnCameraIdleListener(clusterManager);
                        googleMap.setOnMarkerClickListener(clusterManager);
                    }


                }

            }
            override fun onFailure(call: Call<GetPostResponse>, t: Throwable) {

            }
        })
    }

    // cluster's marker click event
    var mClusterItemClickListener: ClusterManager.OnClusterItemClickListener<CustomClusterItem> =
        ClusterManager.OnClusterItemClickListener<CustomClusterItem> { item ->
//        binding.bottomSheetSearch.visibility = View.VISIBLE
//        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//        }

            showBottomSheet(item.getTag()!!)
            true
        }

    fun showBottomSheet(post: GetPost) {

    }


    override fun onResume() {
        binding.mapViewSearch.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapViewSearch.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapViewSearch.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapViewSearch.onLowMemory()
    }

}
