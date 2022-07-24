package com.ayizor.afeme.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import android.widget.TextView.BufferType
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.ItemPostViewPagerAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.ActivityDetailsBinding
import com.ayizor.afeme.databinding.ItemBottomSheetMoreBinding
import com.ayizor.afeme.helper.CustomSpannable
import com.ayizor.afeme.model.inmodels.Image
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.model.response.PostResponse
import com.ayizor.afeme.utils.Utils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class DetailsActivity : BaseActivity(), OnMapReadyCallback {

    lateinit var binding: ActivityDetailsBinding
    var dataService: ApiInterface? = null
    val TAG: String = DetailsActivity::class.java.simpleName
    lateinit var supportMapFragment: SupportMapFragment
    lateinit var viewPagerAdapter: ItemPostViewPagerAdapter
    lateinit var phoneNumber: String
    private var viewPager: ViewPager2? = null
    var id by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
        setSupportActionBar(binding.toolbar)
    }

    private fun inits() {
        dataService = Client.getClient(this)?.create(ApiInterface::class.java)
        supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_map_details) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
        val extras = intent.extras
        if (extras != null) {
            id = extras.getInt("POST_ID")
            getPost(id)
        }

        binding.llPlan.setOnClickListener {
            viewPager?.currentItem = viewPagerAdapter.itemCount - 1
        }
        binding.btnMore.setOnClickListener {
            showSettingsBottomsheet(id)
        }
    }

    private fun getPost(id: Int) {
        dataService!!.getSinglePost(id)
            .enqueue(object : Callback<PostResponse> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<PostResponse>,
                    response: Response<PostResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.data?.let { displayPostDatas(it) }
                        phoneNumber = response.body()?.data?.user?.user_phone_number.toString()
//                binding.rvSellType.visibility = View.VISIBLE
//                binding.progressBar.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {

                }
            })

    }

    private fun displayPostDatas(post: GetPost) {

        post.post_images?.let { setupViewPager(it) }
        //  post.user?.let { displayUserDatas(it) }
        val locationName = post.post_latitude?.let {
            post.post_longitude?.let { it1 ->
                Utils.getCoordinateName(
                    this,
                    it.toDouble(),
                    it1.toDouble()
                )
            }
        }
        if (locationName != null) {
            val state = locationName.state
            val city = locationName.city
            if (!city.isNullOrEmpty()) {
                binding.tvFullLocation.text = state + ", " + city
            } else {
                binding.tvFullLocation.text = state
            }


            binding.tvDescription.text = post.post_description.toString()
            makeTextViewResizable(binding.tvDescription, 3, getString(R.string.view_more), true)
            binding.tvPriceMain.text = post.post_price_usd
            binding.tvBuindingArea.text = post.post_area?.total_area
            //set -> if language changed
            binding.tvBuindingAppointment.text = post.post_building_type?.category_name_en
            if (!post.post_flat.isNullOrEmpty()) {
                binding.tvBuildingFloor.text = post.post_floor + " of " + post.post_flat
            } else {
                binding.tvBuildingFloor.text = post.post_floor
            }



        }
    }


    fun setupViewPager(postsList: ArrayList<Image>) {
        viewPagerAdapter = ItemPostViewPagerAdapter(postsList, context)
        viewPager?.currentItem = 1
        viewPager = binding.viewpager
        viewPager?.offscreenPageLimit = 3
        viewPager!!.adapter = viewPagerAdapter
        viewPager!!.clipToPadding = false
        viewPager!!.clipChildren = false
        viewPager!!.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER


    }

    fun showSettingsBottomsheet(post_id: Int) {
        val sheetDialog = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
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

    private fun makeTextViewResizable(
        tv: TextView,
        maxLine: Int,
        expandText: String,
        viewMore: Boolean
    ) {
        if (tv.tag == null) {
            tv.tag = tv.text
        }
        val vto: ViewTreeObserver = tv.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val text: String
                val lineEndIndex: Int
                val obs: ViewTreeObserver = tv.viewTreeObserver
                obs.removeOnGlobalLayoutListener(this)
                if (maxLine == 0) {
                    lineEndIndex = tv.layout.getLineEnd(0)
                    text = tv.text.subSequence(0, lineEndIndex - expandText.length + 1)
                        .toString() + " " + expandText
                } else if (maxLine > 0 && tv.lineCount >= maxLine) {
                    lineEndIndex = tv.layout.getLineEnd(maxLine - 1)
                    text = tv.text.subSequence(0, lineEndIndex - expandText.length + 1)
                        .toString() + " " + expandText
                } else {
                    lineEndIndex = tv.layout.getLineEnd(tv.layout.lineCount - 1)
                    text = tv.text.subSequence(0, lineEndIndex).toString() + " " + expandText
                }
                tv.text = text
                tv.movementMethod = LinkMovementMethod.getInstance()
                tv.setText(
                    addClickablePartTextViewResizable(
                        SpannableString(tv.text.toString()), tv, lineEndIndex, expandText,
                        viewMore
                    ), BufferType.SPANNABLE
                )
            }
        })
    }


    private fun addClickablePartTextViewResizable(
        strSpanned: Spanned,
        tv: TextView,
        maxLine: Int,
        spanableText: String,
        viewMore: Boolean
    ): SpannableStringBuilder? {
        val str = strSpanned.toString()
        val ssb = SpannableStringBuilder(strSpanned)

        if (str.contains(spanableText)) {
            ssb.setSpan(object : CustomSpannable(false) {
                override fun onClick(widget: View) {
                    super.onClick(widget)
                    tv.layoutParams = tv.layoutParams
                    tv.setText(tv.tag.toString(), BufferType.SPANNABLE)
                    tv.invalidate()
                    if (viewMore) {
                        makeTextViewResizable(tv, -1, "View less", false)
                    } else {
                        makeTextViewResizable(tv, 3, "View more", true)
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length, 0)
        }
        return ssb
    }


    override fun onMapReady(googleMap: GoogleMap) {
        val extras = intent.extras
        if (extras != null) {
            val latitude = extras.getString("POST_LATITUDE", "").toDouble()
            val longitude = extras.getString("POST_LONGITUDE", "").toDouble()
            Log.e(TAG, latitude.toString() + "" + longitude.toString())
            val postLocation = LatLng(latitude, longitude)
            googleMap.uiSettings.isZoomGesturesEnabled = false;
            googleMap.uiSettings.isScrollGesturesEnabled = false
            googleMap.uiSettings.isMapToolbarEnabled = true;
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(postLocation, 16f))
            // create marker
            val marker: MarkerOptions =
                MarkerOptions().position(postLocation)
            // Changing marker icon
            marker.icon(bitmapDescriptorFromVector(this, R.drawable.ic_home_marker))
            // adding marker
            googleMap.addMarker(marker)

        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}