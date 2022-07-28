package com.ayizor.afeme.fragment.searchfragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayizor.afeme.R
import com.ayizor.afeme.activity.DetailsActivity
import com.ayizor.afeme.adapter.ItemMainMapPostsAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.FragmentMapBinding
import com.ayizor.afeme.databinding.ItemBottomSheetMoreBinding
import com.ayizor.afeme.manager.PrefsManager
import com.ayizor.afeme.model.CustomClusterItem
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.model.response.GetPostResponse
import com.ayizor.afeme.model.response.PostResponse
import com.ayizor.afeme.utils.Logger
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.maps.android.clustering.ClusterManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapFragment : Fragment(), ItemMainMapPostsAdapter.OnPostItemClickListener,
    ItemMainMapPostsAdapter.OnActionsButtonClickListener,
    ItemMainMapPostsAdapter.OnLikeButtonClickListener, OnMapReadyCallback {
    //binding
    lateinit var binding: FragmentMapBinding

    //tag for this fragment
    val TAG: String = MapFragment::class.java.simpleName

    //service for call main api
    var dataService: ApiInterface? = null

    //arraylist for posts
    var postsList: ArrayList<GetPost> = ArrayList()
    lateinit var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>

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
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetSearch)
        binding.rvPost.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        dataService?.getAllPosts()?.enqueue(object : Callback<GetPostResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<GetPostResponse>,
                response: Response<GetPostResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    //
                    Logger.e(TAG, response.body()?.data.toString())
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

                        clusterManager.setOnClusterClickListener {

                            Logger.e(TAG, clusterManager.markerCollection.markers.toString())
                            return@setOnClusterClickListener true
                        }
                        clusterManager.setOnClusterItemClickListener {
                            binding.bottomSheetSearch.visibility = View.VISIBLE
                            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                            }

                            it.getTag()?.post_id?.let { it1 -> getPosts(it1) }

                            return@setOnClusterItemClickListener true
                        }


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
            binding.bottomSheetSearch.visibility = View.VISIBLE
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

//            val list: ArrayList<GetPost> = ArrayList()
//            list.add(item.getTag()!!)
            item.getTag()?.post_id?.let { getPosts(it) }
            Logger.e(TAG, item.getTag().toString())
            true
        }
    var mClusterClickListener: ClusterManager.OnClusterClickListener<CustomClusterItem> =
        ClusterManager.OnClusterClickListener<CustomClusterItem> { item ->
            binding.bottomSheetSearch.visibility = View.VISIBLE
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            val list: ArrayList<GetPost> = ArrayList()




            true
        }

    override fun onPostItemClickListener(id: Int, latitude: String, longitude: String) {
        val intent = Intent(requireContext(), DetailsActivity::class.java)
        intent.putExtra("POST_ID", id)
        intent.putExtra("POST_LATITUDE", latitude)
        intent.putExtra("POST_LONGITUDE", longitude)
        startActivity(intent)
    }

    override fun onActionsButtonClickListener(id: Int) {
        showSettingsBottomsheet(id)
    }

    private fun showBottomSheet(post: GetPost) {


    }

    private fun refreshPostsAdapter(filters: ArrayList<GetPost>) {
        Logger.e(TAG, "filter" + filters.toString())
        val adapter =
            activity?.applicationContext?.let {
                ItemMainMapPostsAdapter(
                    it,
                    filters,
                    this,
                    this,
                    this
                )
            }
        binding.rvPost.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    private fun getPosts(id: Int) {

        dataService?.getSinglePost(id)?.enqueue(object : Callback<PostResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<PostResponse>,
                response: Response<PostResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {


//                    refreshPostsAdapter(list)
                    val list: ArrayList<GetPost> = ArrayList()
                    response.body()?.data?.let { list.add(it) }
                    val adapter =
                        activity?.applicationContext?.let {
                            ItemMainMapPostsAdapter(
                                it,
                                list,
                                this@MapFragment,
                                this@MapFragment,
                                this@MapFragment
                            )
                        }
                    binding.rvPost.adapter = adapter
                    adapter?.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
            }

        })

    }

    fun showSettingsBottomsheet(post_id: Int) {
        val sheetDialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        val bottomSheetBinding: ItemBottomSheetMoreBinding =
            ItemBottomSheetMoreBinding.inflate(layoutInflater)
        sheetDialog.setContentView(bottomSheetBinding.root)


        bottomSheetBinding.llBsNote.setOnClickListener {

        }
        bottomSheetBinding.llBsShare.setOnClickListener {

        }
        bottomSheetBinding.llBsReport.setOnClickListener {

        }
        bottomSheetBinding.llBsHidePost.setOnClickListener {

        }
        bottomSheetBinding.ivBsClose.setOnClickListener {
            sheetDialog.dismiss()
        }
        sheetDialog.show();
        sheetDialog.window?.attributes?.windowAnimations = R.style.DialogAnimaton;
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

    override fun onLikeButtonClickListener(id: Int, likeStatus: Boolean) {

    }

}
