package com.ayizor.afeme.adapter

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.ItemMainPostBinding
import com.ayizor.afeme.model.inmodels.Image
import com.ayizor.afeme.model.post.GetPost


class ItemMainPostsAdapter(
    val context: Context,
    var postsList: ArrayList<GetPost>,
    private val onPostItemClickListener: OnPostItemClickListener,
    private val onActionsButtonClickListener: OnActionsButtonClickListener,
    private val onLikeButtonClickListener: OnLikeButtonClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val TAG: String = ItemMainPostsAdapter::class.java.simpleName
    private lateinit var binding: ItemMainPostBinding
    private var viewPager: ViewPager2? = null
    private var adapter: ItemPostViewPagerAdapter? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        binding = ItemMainPostBinding.inflate(LayoutInflater.from(context), parent, false)
        return ItemMainPostViewHolder(binding)


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        with(holder) {
            with(postsList[position]) {

                binding.tvPrice.text = post_price_usd

                if (post_images != null) {
                    setupViewPager(post_images)
                }
                if (post_isLiked == true) {
                    binding.ivLike.setImageResource(R.drawable.ic_heart_full)
                } else {
                    binding.ivLike.setImageResource(R.drawable.ic_heart_outline)
                }

//post like click listener
                binding.btnCall.setOnClickListener {
                    val intent = Intent(Intent.ACTION_CALL)

                    intent.data = Uri.parse("tel:" + user?.user_phone_number)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent)


                }
                binding.ivLike.setOnClickListener {
                    if (post_isLiked == true) {
                        binding.ivLike.setImageResource(R.drawable.ic_heart_outline)
                        onLikeButtonClickListener.onLikeButtonClickListener(post_id!!, false)
                    } else {
                        binding.ivLike.setImageResource(R.drawable.ic_heart_full)
                        onLikeButtonClickListener.onLikeButtonClickListener(post_id!!, true)
                    }
                    post_isLiked = !post_isLiked!!
                }

                //post click listener
                binding.llMain.setOnClickListener {
                    if (post_id != null) {
                        if (post_latitude != null) {
                            if (post_longitude != null) {
                                onPostItemClickListener.onPostItemClickListener(
                                    post_id,
                                    post_latitude,
                                    post_longitude
                                )
                            }
                        }
                    }
                }
                //actions bottomsheet click listener
                binding.btnMore.setOnClickListener {
                    if (post_id != null) {
                        onActionsButtonClickListener.onActionsButtonClickListener(post_id)
                    }
                }
            }

        }

    }


    private fun setupViewPager(postsList: ArrayList<Image>) {
        adapter = ItemPostViewPagerAdapter(postsList, context)
        viewPager?.currentItem = 1;
        viewPager = binding.viewpager
        viewPager?.offscreenPageLimit = 3
        viewPager!!.adapter = adapter
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


    override fun getItemCount(): Int {
        return postsList.size - 1
    }


    inner class ItemMainPostViewHolder(val binding: ItemMainPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnPostItemClickListener {
        fun onPostItemClickListener(id: Int, latitude: String, longitude: String)
    }

    interface OnActionsButtonClickListener {
        fun onActionsButtonClickListener(id: Int)
    }

    interface OnLikeButtonClickListener {
        fun onLikeButtonClickListener(id: Int, likeStatus: Boolean)
    }
}


