package com.ayizor.afemev2.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.model.inmodels.Image
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afemev2.R
import com.ayizor.afemev2.databinding.ItemMainPostBinding
import com.ayizor.afemev2.databinding.ItemMainPostViewPagerBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target



class ItemPostViewPagerAdapter(
    val operatorArrayList: ArrayList<GetPost>,
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


    }

    override fun getItemCount(): Int {
        return operatorArrayList.size
    }

    inner class ItemPostViewPagerViewHolder(val binding: ItemMainPostViewPagerBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnViewPagerItemClickListener {
        fun onViewPagerItemClickListener(id: Int)
    }

}