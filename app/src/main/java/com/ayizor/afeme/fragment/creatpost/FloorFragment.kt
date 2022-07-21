package com.ayizor.afeme.fragment.creatpost

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.ayizor.afeme.R
import com.ayizor.afeme.databinding.FragmentFloorBinding
import com.ayizor.afeme.manager.PostPrefsManager
import com.ayizor.afeme.model.inmodels.Floor


class FloorFragment : Fragment() {

    lateinit var binding: FragmentFloorBinding
    val TAG: String = BuildningTypeFragment::class.java.simpleName
    var fragmentNumber = 6
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFloorBinding.inflate(inflater, container, false)
        inits()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun inits() {
        activity?.findViewById<ProgressBar>(R.id.progress_bar_main_creat_post)?.progress =
            fragmentNumber
        binding.btnNext.setOnClickListener {
            if (validFloorHouse() && validApartmentFloor()) {
                val floor = Floor(
                    binding.etFloorsInTheHouse.editText?.text.toString(),
                    binding.etApartmentFloor.editText?.text.toString(),
                )
                PostPrefsManager(requireContext()).storeFloor(floor)
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    .replace(R.id.fragment_container_creat_post, PhotosFragment())
                    .addToBackStack(FloorFragment::class.java.name).commit()
            }
        }

    }


    private fun validApartmentFloor(): Boolean {
        val nomer: String = binding.etApartmentFloor.editText?.text.toString()
        return if (nomer.isEmpty()) {
            binding.etApartmentFloor.error = "Please enter Apartment Floor"
            false
        } else {
            binding.etApartmentFloor.error = null
            true
        }
    }

    private fun validFloorHouse(): Boolean {
        val nomer: String = binding.etFloorsInTheHouse.editText?.text.toString()
        return if (nomer.isEmpty()) {
            binding.etFloorsInTheHouse.error = "Please enter Floors In The House"
            false
        } else {
            binding.etFloorsInTheHouse.error = null
            true
        }
    }
}