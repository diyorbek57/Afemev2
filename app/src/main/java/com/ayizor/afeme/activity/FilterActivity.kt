package com.ayizor.afeme.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ayizor.afeme.adapter.ItemCategorySpinnerAdapter
import com.ayizor.afeme.adapter.ItemPostTypeSpinnerAdapter
import com.ayizor.afeme.api.main.ApiInterface
import com.ayizor.afeme.api.main.Client
import com.ayizor.afeme.databinding.ActivityFilterBinding
import com.ayizor.afeme.databinding.ItemPriceDialogBinding
import com.ayizor.afeme.model.Category
import com.ayizor.afeme.model.inmodels.SellType
import com.ayizor.afeme.model.response.CategoryResponse
import com.ayizor.afeme.model.response.SellResponse
import com.ayizor.afeme.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FilterActivity : AppCompatActivity() {
    lateinit var binding: ActivityFilterBinding
    var dataService: ApiInterface? = null
    val TAG: String = FilterActivity::class.java.simpleName
    val post_types: ArrayList<SellType> = ArrayList()
    val building_types: ArrayList<Category> = ArrayList()
    var dialog: Dialog? = null
    lateinit var dialogBinding: ItemPriceDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inits()
    }

    private fun inits() {
        dataService = Client.getClient(this)?.create(ApiInterface::class.java)

        binding.rlPrice.setOnClickListener {


            val builder: AlertDialog.Builder? = AlertDialog.Builder(this)
            dialogBinding = ItemPriceDialogBinding.inflate(layoutInflater)


            builder?.setView(dialogBinding.root)
            builder?.setCancelable(true)
            dialog = builder?.create()
            dialog?.show()


            dialogBinding.etPriceFrom.setEndIconOnClickListener {
                dialogBinding.etPriceFrom.editText?.setText("")
            }
            dialogBinding.etPriceTo.setEndIconOnClickListener {
                dialogBinding.etPriceTo.editText?.setText("")
            }


        }
        getAllSellTypes()
        getAllCategory()
    }


    private fun getAllSellTypes() {
        dataService!!.getAllSellTypes().enqueue(object : Callback<SellResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<SellResponse>,
                response: Response<SellResponse>
            ) {
                Logger.d(TAG, response.body().toString())
                val sellTypes = response.body()?.data
                if (!sellTypes.isNullOrEmpty()) {
                    for (i in 0 until sellTypes.size) {
                        post_types.add(sellTypes[i])
                    }
                    val spinnerAdapter = ItemPostTypeSpinnerAdapter(this@FilterActivity, post_types)
                    binding.spinnerPostType.adapter = spinnerAdapter

                }
            }

            override fun onFailure(call: Call<SellResponse>, t: Throwable) {
                t.message?.let { Logger.d(TAG, it) }
                //progressBar!!.visibility = View.GONE
            }
        })

    }

    private fun getAllCategory() {
        dataService!!.getAllCategory().enqueue(object : Callback<CategoryResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                Logger.d(TAG, response.body().toString())
                val category = response.body()?.data

                if (!category.isNullOrEmpty()) {
                    for (i in 0 until category.size) {
                        building_types.add(category[i])
                    }

                    val spinnerAdapter = ItemCategorySpinnerAdapter(this@FilterActivity, building_types)
                    binding.spinnerCategory.adapter = spinnerAdapter
                }


            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                t.message?.let { Logger.d(TAG, it) }
            }
        })
    }

//    private fun getAllRegions() {
//        dataService!!.getRegions().enqueue(object : Callback<RegionsResponse> {
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onResponse(
//                call: Call<RegionsResponse>,
//                response: Response<RegionsResponse>
//            ) {
//                Logger.d(TAG, response.body().toString())
//                val category = response.body()?.data
//                val spinnerItems: MutableList<IconSpinnerItem> = ArrayList()
//                for (i in 0 until category?.size!!) {
//                    category[i].region_name_en?.let { IconSpinnerItem(it) }?.let {
//                        spinnerItems.add(
//                            it
//                        )
//                    }
//                }
//                val iconSpinnerAdapter = IconSpinnerAdapter(binding.spinnerPostType)
//                binding.spinnerPostType.setSpinnerAdapter(iconSpinnerAdapter)
//                binding.spinnerPostType.setItems(spinnerItems)
//
//            }
//
//            override fun onFailure(call: Call<RegionsResponse>, t: Throwable) {
//                t.message?.let { Logger.d(TAG, it) }
//                //progressBar!!.visibility = View.GONE
//            }
//        })
//
//    }
}