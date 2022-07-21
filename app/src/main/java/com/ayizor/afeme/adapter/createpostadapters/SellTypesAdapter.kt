package com.ayizor.afeme.adapter.createpostadapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.api.main.Api
import com.ayizor.afeme.databinding.ItemCreatePostSellTypeBinding
import com.ayizor.afeme.model.inmodels.SellType
import com.bumptech.glide.Glide

class SellTypesAdapter(
    var context: Context,
    var categoryList: ArrayList<SellType>,
    private val onSellTypeItemClickListener: OnSellTypeItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var binding: ItemCreatePostSellTypeBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        binding =
            ItemCreatePostSellTypeBinding.inflate(LayoutInflater.from(context), parent, false)
        return BuildingTypeViewHolder(binding)


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemViewType) {
            with(categoryList[position]) {
                binding.tvSellTypeName.text = post_type_name_en
                if (post_type_name_en != null) {
                    Glide.with(context)
                        .load(Api.CATEGORY_IMAGE_URL + category_icon)
                        .into(binding.ivSellType)
                }
                binding.cvSellType.setOnClickListener {
                    if (category_id != null) {
                        onSellTypeItemClickListener.onSellTypeItemClickListener(
                            category_id
                        )
                    }
                }

            }

        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }


    inner class BuildingTypeViewHolder(val binding: ItemCreatePostSellTypeBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnSellTypeItemClickListener {
        fun onSellTypeItemClickListener(id: Int)
    }
}