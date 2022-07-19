package com.ayizor.afeme.activity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import android.widget.TextView.BufferType
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.ItemPostViewPagerAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.databinding.ActivityDetailsBinding
import com.ayizor.afeme.helper.CustomSpannable
import com.ayizor.afeme.model.inmodels.Image
import com.ayizor.afeme.model.post.GetPost
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailsActivity : BaseActivity(), OnMapReadyCallback {

    lateinit var binding: ActivityDetailsBinding
    var dataService: ApiInterface? = null
    val TAG: String = DetailsActivity::class.java.simpleName
    lateinit var supportMapFragment: SupportMapFragment
    lateinit var viewPagerAdapter: ItemPostViewPagerAdapter
    lateinit var phoneNumber: String
    private var viewPager: ViewPager2? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
        setSupportActionBar(binding.toolbar)
    }

    private fun inits() {

    }

    private fun displayPostDatas(post: GetPost) {
        //  post.user?.let { displayUserDatas(it) }
//        setupViewPager(post)
//        val locationName = post.post_latitude?.let {
//            post.post_longitude?.let { it1 ->
//                Utils.getCoordinateName(
//                    this,
//                    it.toDouble(),
//                    it1.toDouble()
//                )
//            }
//        }
//        if (locationName != null) {
//            val state = locationName.state
//            val city = locationName.city
//            if (!city.isNullOrEmpty()) {
//                binding.tvLocationDetails.text = state +", "+ city
//            } else {
//                binding.tvLocationDetails.text = state
//            }
//            binding.tvNamePostDetails.text = state+", "+ post.post_rooms+" rooms"

        // makeTextViewResizable(, 3, "View More", true)
        binding.tvPriceMain.text = post.post_price_usd
        //binding.tvDescriptionDetails.text = post.post_description.toString()
        // binding.tvTypeDetails.text = post.post_building_type?.category_name_en.toString()

        //binding.tvDescriptionDetails.text = post.post_description

    }


    private fun setupViewPager(postsList: ArrayList<Image>) {
        viewPagerAdapter = ItemPostViewPagerAdapter(postsList, context)
        viewPager?.currentItem = 1;
        viewPager = binding.viewpager
        viewPager?.offscreenPageLimit = 3
        viewPager!!.adapter = viewPagerAdapter
        viewPager!!.clipToPadding = false
        viewPager!!.clipChildren = false
        viewPager!!.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        val transformer = CompositePageTransformer()

        transformer.addTransformer(MarginPageTransformer(30))

        transformer.addTransformer { page, position ->
            //val v = 1 - abs(position)


        }
        viewPager!!.setPageTransformer(transformer)

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

            val postLocation = LatLng(latitude, longitude)
            googleMap.uiSettings.isZoomGesturesEnabled = false;
            googleMap.uiSettings.isScrollGesturesEnabled = false
            googleMap.uiSettings.isMapToolbarEnabled = true;
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(postLocation, 16f))
            // create marker
            val marker: MarkerOptions =
                MarkerOptions().position(postLocation)
            // Changing marker icon
            marker.icon(bitmapDescriptorFromVector(this, R.drawable.ic_heart_full))
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