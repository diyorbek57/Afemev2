package com.ayizor.afeme.fragment.creatpost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.FragmentAreaBinding
import com.ayizor.afeme.manager.PostPrefsManager
import com.ayizor.afeme.model.inmodels.Area


class AreaFragment : Fragment() {

    lateinit var binding: FragmentAreaBinding
    val TAG: String = BuildningTypeFragment::class.java.simpleName
    var fragmentNumber = 5
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAreaBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    private fun inits() {
        activity?.findViewById<ProgressBar>(R.id.progress_bar_main_creat_post)?.progress =
            fragmentNumber
        binding.btnNext.setOnClickListener {
            if (validTotalArea() && validLivingArea() && validKitchenArea()) {
                val area = Area(
                    binding.etTotalArea.editText?.text.toString(),
                    binding.etLivingSpace.editText?.text.toString(),
                    binding.etKitchenArea.editText?.text.toString()
                )
                PostPrefsManager(requireContext()).storeArea(area)
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    .replace(R.id.fragment_container_creat_post, FloorFragment())
                    .addToBackStack(AreaFragment::class.java.name).commit()
            }
        }


    }

    private fun validTotalArea(): Boolean {
        val nomer: String = binding.etTotalArea.editText?.text.toString()
        return if (nomer.isEmpty()) {
            binding.etTotalArea.error = "Please enter Total area"
            false
        } else {
            binding.etTotalArea.error = null
            true
        }
    }

    private fun validLivingArea(): Boolean {
        val nomer: String = binding.etLivingSpace.editText?.text.toString()
        return if (nomer.isEmpty()) {
            binding.etLivingSpace.error = "Please enter Living area"
            false
        } else {
            binding.etLivingSpace.error = null
            true
        }
    }

    private fun validKitchenArea(): Boolean {
        val nomer: String = binding.etKitchenArea.editText?.text.toString()
        return if (nomer.isEmpty()) {
            binding.etKitchenArea.error = "Please enter Kitchen area"
            false
        } else {
            binding.etKitchenArea.error = null
            true
        }
    }


}