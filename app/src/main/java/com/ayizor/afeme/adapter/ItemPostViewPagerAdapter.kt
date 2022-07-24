package com.ayizor.afeme.adapter


import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.ItemMainPostViewPagerBinding
import com.ayizor.afeme.model.inmodels.Image
import com.ayizor.afeme.model.post.GetPost
import com.bumptech.glide.Glide


class ItemPostViewPagerAdapter(
    val postsList: ArrayList<Image>,
    val context: Context
) :
    RecyclerView.Adapter<ItemPostViewPagerAdapter.ItemPostViewPagerViewHolder>() {

    val TAG: String = ItemPostViewPagerAdapter::class.java.simpleName
    private lateinit var binding: ItemMainPostViewPagerBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPostViewPagerViewHolder {
        binding = ItemMainPostViewPagerBinding.inflate(LayoutInflater.from(context), parent, false)
        return ItemPostViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemPostViewPagerViewHolder, position: Int) {

        with(holder) {
            with(postsList[position]) {
                    Glide.with(context)
                        .load(image_url).error(R.drawable.default_profile_image)
                        .into(binding.ivViewpager)


            }
        }


    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    inner class ItemPostViewPagerViewHolder(val binding: ItemMainPostViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnViewPagerItemClickListener {
        fun onViewPagerItemClickListener(id: Int)
    }

}