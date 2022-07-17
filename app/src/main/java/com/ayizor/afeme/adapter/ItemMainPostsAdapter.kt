package com.ayizor.afeme.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.ayizor.afeme.databinding.ItemMainPostBinding
import com.ayizor.afeme.model.inmodels.Image
import com.ayizor.afeme.model.post.GetPost


class ItemMainPostsAdapter(
    var context: Context,
    var postsList: ArrayList<GetPost>,
    private val onPostItemClickListener: OnPostItemClickListener,
    private val onActionsButtonClickListener: OnActionsButtonClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        return postsList.size
    }


    inner class ItemMainPostViewHolder(val binding: ItemMainPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnPostItemClickListener {
        fun onPostItemClickListener(id: Int, latitude: String, longitude: String)
    }

    interface OnActionsButtonClickListener {
        fun onActionsButtonClickListener(id: Int)
    }
}


