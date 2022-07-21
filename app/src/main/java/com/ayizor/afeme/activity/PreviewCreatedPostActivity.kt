package com.ayizor.afeme.activity


import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.exifinterface.media.ExifInterface
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.ayizor.afeme.R
import com.ayizor.afeme.adapter.createpostadapters.PreviewViewPagerAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.ActivityPreviewCreatedPostBinding
import com.ayizor.afeme.manager.PostPrefsManager
import com.ayizor.afeme.model.inmodels.BuildingMaterial
import com.ayizor.afeme.model.inmodels.Image
import com.ayizor.afeme.model.post.Post
import com.ayizor.afeme.model.response.BuildingMaterialResponse
import com.ayizor.afeme.model.response.MainResponse
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class PreviewCreatedPostActivity : BaseActivity() {

    lateinit var binding: ActivityPreviewCreatedPostBinding
    var dataService: ApiInterface? = null
    val TAG: String = PreviewCreatedPostActivity::class.java.simpleName
    val imagesUrls: ArrayList<String> = ArrayList()
    lateinit var uriList: ArrayList<Image>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewCreatedPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        uriList = PostPrefsManager(this).loadImages()!!
        inits()
    }

    @SuppressLint("ResourceType")
    private fun inits() {
        dataService = Client.getClient(this)?.create(ApiInterface::class.java)
        refreshViewPagerAdapter()
        displaySavedDatas()
        getAllBuildingMaterials()
        binding.chipGroupBuildingMaterials.setOnCheckedStateChangeListener { group, checkedIds ->
            Toast.makeText(
                this,
                checkedIds.toString().replace("[", "").replace("]", ""),
                Toast.LENGTH_SHORT
            )
                .show()
        }
        binding.btnPublish.setOnClickListener {
            imagesUrls.clear()
            uploadImages()

        }

    }

    private fun uploadImages() {

        for (i in 0 until uriList.size) {
            saveBitmapToFile(getFile(Uri.parse(uriList[i].image_url)))?.let { getFileUrl(it) }
        }
    }

    fun getMultipartBody(key: String, file: File): MultipartBody.Part {
        val reqFile: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(key, file.name, reqFile)
    }


    private fun uploadPost(imagesUrls: ArrayList<String>) {
        showLoading(this@PreviewCreatedPostActivity)
        val post_price = binding.etPrice.editText?.text.toString()
        val post_type = PostPrefsManager(this@PreviewCreatedPostActivity).loadPostType()
        val post_building_type =
            PostPrefsManager(this@PreviewCreatedPostActivity).loadBuildingType()
        val post_built_year = binding.etBuiltYear.editText?.text.toString()
        val post_description = binding.tvDescriptionDetails.text.toString()
        val post_latitude = PostPrefsManager(this@PreviewCreatedPostActivity).loadLatitude()
        val post_longitude = PostPrefsManager(this@PreviewCreatedPostActivity).loadLongitude()
        val post_floor = PostPrefsManager(this@PreviewCreatedPostActivity).loadFloor().house_floor
        val post_flat =
            PostPrefsManager(this@PreviewCreatedPostActivity).loadFloor().apartment_floor
        val post_total_area =
            PostPrefsManager(this@PreviewCreatedPostActivity).loadArea().total_area
        val post_kitchen_area =
            PostPrefsManager(this@PreviewCreatedPostActivity).loadArea().kitchen_area
        val post_living_area =
            PostPrefsManager(this@PreviewCreatedPostActivity).loadArea().living_area
        val post_rooms = binding.etRooms.editText?.text.toString()
        // val post_material =


        val post = Post(
            imagesUrls,//1
            null,//r
            post_building_type.toString(),//1
            post_type.toString(),//r//1
            post_latitude,//r//1
            post_longitude,//r//1
            post_price,//r
            null,//r
            null,
            post_total_area,
            post_kitchen_area,
            post_living_area,//r
            post_built_year,//r//1
            post_rooms,//r//1
            "5",//r//1
            imagesUrls,//1
            post_description,//r
            "12",//r//1
            "23",//r//1
            "1",//r//1
            "1",//r//1
            "1",//1
            post_floor,
            post_flat,
            null,
            null
        )

        //    Logger.d(TAG, "data: "+dataService!!.createPost(post).request().body)
//        val postAsList: ArrayList<Post> = ArrayList()
//        postAsList.add(post)

        dataService!!.createPost(post).enqueue(object : Callback<MainResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<MainResponse>,
                response: Response<MainResponse>
            ) {
                if (response.isSuccessful) {
                    dismissLoading()
                    finish()

                } else {
                    dismissLoading()
                }
            }

            override fun onFailure(call: Call<MainResponse>, t: Throwable) {
            }

        })


    }

    private fun displaySavedDatas() {
        val area = PostPrefsManager(this).loadArea()
        val floor = PostPrefsManager(this).loadFloor()
        binding.etRooms.editText?.setText(PostPrefsManager(this).loadRooms().toString())
        binding.etTotalArea.editText?.setText(area.total_area).toString()
        binding.etKitchenArea.editText?.setText(area.kitchen_area).toString()
        binding.etLivingSpace.editText?.setText(area.living_area).toString()
        binding.etPrice.editText?.setText(PostPrefsManager(this).loadPrice().toString())
        binding.etFloorsInTheHouse.editText?.setText(floor.house_floor).toString()
        binding.tvDescriptionDetails.setText(
            PostPrefsManager(this).loadDescription()
        )
    }

    private fun refreshViewPagerAdapter() {
        val adapter = PostPrefsManager(this).loadImages()?.let { PreviewViewPagerAdapter(this, it) }
        binding.viewpager.adapter = adapter
        binding.viewpager.clipToPadding = false
        binding.viewpager.clipChildren = false
        binding.viewpager.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        val transformer = CompositePageTransformer()

        transformer.addTransformer(MarginPageTransformer(2))
//        transformer.addTransformer { page, position ->
//            val v = 1 - Math.abs(position)
//            page.scaleY = 0.8f + v * 0.2f
//        }
        binding.viewpager.setPageTransformer(transformer)
        binding.viewpager.offscreenPageLimit = PostPrefsManager(this).loadImages()?.size!!
    }

    @SuppressLint("ResourceAsColor")
    private fun addMaterialsChip(item: ArrayList<BuildingMaterial>) {
        for (i in 0 until item.size) {
            val chip = Chip(this)
            val chipDrawable: ChipDrawable = ChipDrawable.createFromAttributes(
                this, null, 0,
                R.style.CustomChipGroupStyle
            )
            chip.id = i + 1
            chip.text = item[i].name
            chip.isCheckable = true
            chip.setChipDrawable(chipDrawable)

            binding.chipGroupBuildingMaterials.addView(chip)
        }


    }

    @SuppressLint("ResourceAsColor")
    private fun addExtraChip(item: ArrayList<BuildingMaterial>) {
        for (i in 0 until item.size) {
            val chip = Chip(this)
            val chipDrawable: ChipDrawable = ChipDrawable.createFromAttributes(
                this, null, 0,
                R.style.CustomChipGroupStyle
            )
            chip.id = i + 1
            chip.text = item[i].name
            chip.isCheckable = true
            chip.setChipDrawable(chipDrawable)

            binding.chipGroupBuildingMaterials.addView(chip)
        }


    }

    private fun getFile(uri: Uri): File {
        val ins = this.contentResolver.openInputStream(uri)
        val file = File.createTempFile(
            "file",
            ".jpg",
            this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        val fileOutputStream = FileOutputStream(file)
        ins?.copyTo(fileOutputStream)
        ins?.close()
        fileOutputStream.close()
        return file
    }

    fun saveBitmapToFile(file: File): File? {
        return try {

            // BitmapFactory options to downsize the image
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            // factor of downsizing the image
            var inputStream = FileInputStream(file)
            val oldExif = ExifInterface(file.path)
            val exifOrientation = oldExif.getAttribute(ExifInterface.TAG_ORIENTATION)
            if (exifOrientation != null) {
                val newExif = ExifInterface(file.path)
                newExif.setAttribute(ExifInterface.TAG_ORIENTATION, exifOrientation)
                newExif.saveAttributes()
            }
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()

            // The new size we want to scale to
            val REQUIRED_SIZE = 75

            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)
            val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()

            // here i override the original image file
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            file
        } catch (e: Exception) {
            null
        }
    }

    private fun getAllBuildingMaterials() {
        dataService!!.getAllBuildingMaterials()
            .enqueue(object : Callback<BuildingMaterialResponse> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<BuildingMaterialResponse>,
                    response: Response<BuildingMaterialResponse>
                ) {
                    response.body()?.data?.let { addMaterialsChip(it as ArrayList<BuildingMaterial>) }
                }

                override fun onFailure(call: Call<BuildingMaterialResponse>, t: Throwable) {
                }
            })

    }

    private fun getFileUrl(file: File) {
        dataService?.uploadFile(getMultipartBody("file", file), file.name, "Service For C Group")
            ?.enqueue(object : Callback<MainResponse> {
                override fun onResponse(
                    call: Call<MainResponse>,
                    response: Response<MainResponse>
                ) {
                    imagesUrls.add(response.body()?.data.toString())
                    if (imagesUrls.size == uriList.size) {
                        uploadPost(imagesUrls)
                    }
                }

                override fun onFailure(call: Call<MainResponse>, t: Throwable) {

                }

            })
    }


}