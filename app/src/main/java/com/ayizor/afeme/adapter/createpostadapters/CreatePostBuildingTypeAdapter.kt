package com.ayizor.afeme.adapter.createpostadapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.api.main.Api
import com.ayizor.afeme.databinding.ItemCreatePostBuildingTypeBinding
import com.ayizor.afeme.model.Category
import com.bumptech.glide.Glide

class CreatePostBuildingTypeAdapter(
    var context: Context,
    var categoryList: ArrayList<Category>,
    private val onBuildingTypeItemClickListener: OnBuildingTypeItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var binding: ItemCreatePostBuildingTypeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        binding =
            ItemCreatePostBuildingTypeBinding.inflate(LayoutInflater.from(context), parent, false)
        return BuildingTypeViewHolder(binding)


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemViewType) {
            with(categoryList[position]) {
                binding.tvBuildingTypeName.text = category_name_uz
                if (category_name_uz != null) {
                    Glide.with(context)
                        .load(Api.CATEGORY_IMAGE_URL + category_icon)
                        .into(binding.ivBuildingTypeIcon)
                }
                binding.cvBuildingType.setOnClickListener {
                    if (category_id != null) {
                        onBuildingTypeItemClickListener.onBuildingTypeItemClickListener(
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


    inner class BuildingTypeViewHolder(val binding: ItemCreatePostBuildingTypeBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnBuildingTypeItemClickListener {
        fun onBuildingTypeItemClickListener(id: Int)
    }
}


