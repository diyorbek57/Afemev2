package com.ayizor.afeme.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ayizor.afeme.R
import com.ayizor.afeme.activity.CreatePostActivity
import com.ayizor.afeme.activity.PreviewCreatedPostActivity
import com.ayizor.afeme.databinding.FragmentServicesBinding
import com.ayizor.afeme.databinding.ItemBottomSheetCreatePostBinding
import com.ayizor.afeme.manager.PostPrefsManager
import com.google.android.material.bottomsheet.BottomSheetDialog


class ServicesFragment : Fragment() {
    lateinit var binding: FragmentServicesBinding
    val TAG: String = ServicesFragment::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicesBinding.inflate(inflater, container, false)

        inits()
        return binding.root
    }

    private fun inits() {

        binding.cvPlaceAd.setOnClickListener {
            callCreatePostActivity()
        }
    }

    private fun callCreatePostActivity() {

        if (PostPrefsManager(requireContext()).loadImages().isNullOrEmpty()) {
            val i = Intent(requireContext(), CreatePostActivity::class.java)
            startActivity(i)
        } else {
            callCratePostBottomSheet()
        }
    }

    private fun callCratePostBottomSheet() {
        val sheetDialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        val bottomSheetBinding: ItemBottomSheetCreatePostBinding =
            ItemBottomSheetCreatePostBinding.inflate(layoutInflater)
        sheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetBinding.btnContinue.setOnClickListener {
            val i = Intent(requireContext(), PreviewCreatedPostActivity::class.java)
            startActivity(i)
        }
        bottomSheetBinding.btnRestart.setOnClickListener {
            PostPrefsManager(requireContext()).clearSavedPostDatas()
            val i = Intent(requireContext(), CreatePostActivity::class.java)
            startActivity(i)
            sheetDialog.cancel()
        }

        sheetDialog.show();
        sheetDialog.window?.attributes?.windowAnimations = R.style.DialogAnimaton;
    }


}