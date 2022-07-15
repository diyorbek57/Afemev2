package com.ayizor.afeme.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.databinding.ItemMainPostViewPagerBinding


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