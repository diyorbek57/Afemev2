package com.ayizor.afeme.adapter.createpostadapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayizor.afeme.databinding.*
import com.ayizor.afeme.model.inmodels.Image
import com.bumptech.glide.Glide

class ChoosePhotoAdapter(
    var context: Context,
    var photoList: ArrayList<Image>,
    var onChoosePhotoItemClickListener: OnChoosePhotoItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var choosedBinding: ItemAddedPhotoBinding
    private lateinit var chooseBinding: ItemAddPhotoBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        choosedBinding =
            ItemAddedPhotoBinding.inflate(LayoutInflater.from(context), parent, false)
        return ChoosePhotoViewHolder(choosedBinding)


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemViewType) {
            with(photoList[position]) {
                Glide.with(context)
                    .load(photoList[position].image_url)
                    .into(choosedBinding.ivImage)
//                chooseBinding.cvAddPhoto.setOnClickListener {
//                    onChoosePhotoItemClickListener.onChoosePhotoItemClickListener(1)
//                }
            }

        }
    }


    override fun getItemCount(): Int {
        return photoList.size
    }


    inner class ChoosePhotoViewHolder : RecyclerView.ViewHolder {
        private var mainBinding: ItemAddedPhotoBinding? = null
        private var secondBinding: ItemAddPhotoBinding? = null

        constructor(binding: ItemAddPhotoBinding) : super(binding.root) {
            secondBinding = binding
        }

        constructor(binding: ItemAddedPhotoBinding) : super(binding.root) {
            mainBinding = binding
        }
    }

    interface OnChoosePhotoItemClickListener {
        fun onChoosePhotoItemClickListener(id: Int)
    }
}