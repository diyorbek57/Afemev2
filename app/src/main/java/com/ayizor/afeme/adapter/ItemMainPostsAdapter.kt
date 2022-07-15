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
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.databinding.ItemMainPostBinding
import kotlin.math.abs


class ItemMainPostsAdapter(
    var context: Context,
    var postsList: ArrayList<GetPost>,
    private val onPostItemClickListener: OnPostItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TAG: String = ItemMainPostsAdapter::class.java.simpleName
    private lateinit var binding: ItemMainPostBinding
    private val viewPager: ViewPager2? = null
    private val adapter: ItemPostViewPagerAdapter? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        binding = ItemMainPostBinding.inflate(LayoutInflater.from(context), parent, false)
        return ItemMainPostViewHolder(binding)


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       inits()
        with(holder) {
            with(postsList[position]) {
                post_images?.let { viewPager?.setOffscreenPageLimit(it.size) };

            }

        }

    }

    private fun inits() {
       setupViewPager()
    }

    private fun setupViewPager() {
        viewPager!!.adapter = adapter
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        val transformer = CompositePageTransformer()

        transformer.addTransformer(MarginPageTransformer(5))
        transformer.addTransformer { page, position ->
            val v = 1 - abs(position)
            page.scaleY = 0.8f + v * 0.2f
        }
        viewPager.setPageTransformer(transformer)

    }


    override fun getItemCount(): Int {
        return postsList.size
    }


    inner class ItemMainPostViewHolder(val binding: ItemMainPostBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnPostItemClickListener {
        fun onPostItemClickListener(id: Int, latitude: String, longitude: String)
    }
}


