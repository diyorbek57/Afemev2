package com.ayizor.afeme.activity


import android.os.Bundle
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.ActivityCreatePostBinding
import com.ayizor.afeme.fragment.creatpost.PostTypeFragment


class CreatePostActivity : BaseActivity() {
    lateinit var binding: ActivityCreatePostBinding
    val TAG: String = CreatePostActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_creat_post, PostTypeFragment())
                .commit()
        }
        inits()
    }

    private fun inits() {
        //  binding.progressBarMainCreatPost.progress = fm.backStackEntryCount

    }


    //    private fun changeIcons() {
//        if (binding.cvSaleTypePost.isChecked) {
//            binding.ivSaleTypePost.setImageResource(R.drawable.ic_creat_post_house_selected);
//        } else {
//            binding.ivSaleTypePost.setImageResource(R.drawable.ic_creat_post_house_unselected);
//        }
//        if (binding.cvMortgageTypePost.isChecked) {
//            binding.ivMortgageTypePost.setImageResource(R.drawable.ic_creat_post_calendar_pen_selected);
//        } else {
//            binding.ivMortgageTypePost.setImageResource(R.drawable.ic_creat_post_calendar_pen_unselected);
//        }
//        if (binding.cvRentTypePost.isChecked) {
//            binding.ivRentTypePost.setImageResource(R.drawable.ic_creat_post_calendar_selected);
//        } else {
//            binding.ivRentTypePost.setImageResource(R.drawable.ic_creat_post_calendar_unselected);
//        }
//
//
//    }
//    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
//    fun onEvent(event: MessageEvent) {
//        binding.progressBarMainCreatPost.progress = event.message
//    }

    override fun onStart() {
        super.onStart()
//        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
//        EventBus.getDefault().unregister(this)
    }


}